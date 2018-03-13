package net.mj.camel.batch.processor.general;

import lombok.Setter;
import net.mj.camel.batch.common.constants.BatchConstants;
import net.mj.camel.batch.common.constants.BatchResultConstants;
import net.mj.camel.batch.loader.DBConfigLoader;
import net.mj.camel.batch.loader.JobConfig;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.support.ServiceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 사용자가 등록한 batch 실행하는 Processor
 *
 */
public class GeneralBatchProcessor extends ServiceSupport implements Processor {

    private final static Logger logger = LoggerFactory.getLogger(GeneralBatchProcessor.class);

    private ApplicationContext applicationContext;

    private JobConfig config = null;

    private Reader reader = null;
    private Worker worker = null;
    private Writer writer = null;

    public GeneralBatchProcessor(ApplicationContext applicationContext, JobConfig config) {
        this.applicationContext = applicationContext;
        this.config = config;
    }


    @Override
    public void process(Exchange exchange) throws Exception {

        try {
            config.isSingleTransaction();

            Object readObject = reader.read();
            Object processObject = worker.work(readObject);
            writer.write(processObject);

            exchange.getIn().setHeader(BatchConstants.BATCH_RESULT.getId(), BatchResultConstants.SUCCESS.getResultCode());
        } catch (Exception e) {
            exchange.getIn().setHeader(BatchConstants.BATCH_RESULT.getId(), BatchResultConstants.FAIL.getResultCode());
            //TODO trace를 String으로 변환 작업
            exchange.getIn().setHeader(BatchConstants.BATCH_ERROR_MESSAGE.getId(), e.toString());
        }
    }

    @Override
    protected void doStart() throws Exception {
        reader = applicationContext.getBean(config.getReaderBeanId(), Reader.class);
        worker = applicationContext.getBean(config.getWorkerBeanId(), Worker.class);
        writer = applicationContext.getBean(config.getWriterBeanId(), Writer.class);

        writer.start();
        worker.start();
        reader.start();
    }

    @Override
    protected void doStop() throws Exception {
        if(reader != null) {
            reader.stop();
        }

        if(worker != null) {
            worker.stop();
        }

        if(writer != null) {
            writer.stop();
        }
    }

}
