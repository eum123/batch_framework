package net.mj.camel.batch.router.db;

import net.mj.camel.batch.history.EndPointWriter;
import net.mj.camel.batch.history.StartPointWriter;
import net.mj.camel.batch.loader.JobConfig;
import net.mj.camel.batch.processor.general.GeneralBatchProcessor;
import net.mj.camel.batch.router.BatchRouter;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class DBBatchRouter extends RouteBuilder implements BatchRouter {

    private JobConfig jobConfig;
    private ApplicationContext applicationContext = null;

    public DBBatchRouter(ApplicationContext applicationContext, JobConfig jobConfig) {
        this.applicationContext = applicationContext;
        this.jobConfig = jobConfig;
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {

    }

    @Override
    public void configure() throws Exception {
        StartPointWriter startPointWriter = (StartPointWriter)applicationContext.getBean("startPointWriter");
        EndPointWriter endPointWriter = (EndPointWriter)applicationContext.getBean("endPointWriter");

        from(makeQuartzString())
                .process(startPointWriter)
                .process(new GeneralBatchProcessor(applicationContext, jobConfig))
                .process(endPointWriter);
    }

    private String makeQuartzString() {
        return "quartz2://" + jobConfig.getJobId() + "/" + jobConfig.getJobId() + "_timer?cron="
                + jobConfig.getSchedule().replaceAll(" ", "+");
    }

}
