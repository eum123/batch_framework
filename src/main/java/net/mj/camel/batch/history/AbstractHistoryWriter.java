package net.mj.camel.batch.history;

import lombok.Getter;
import net.mj.camel.batch.db.jpa.entity.BatchHistoryEntity;
import net.mj.camel.batch.db.jpa.entity.BatchJobEntity;
import net.mj.camel.batch.db.jpa.repository.BatchHistoryRepository;
import net.mj.camel.batch.loader.DBConfigLoader;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.support.ServiceSupport;
import org.apache.camel.util.StopWatch;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public abstract class AbstractHistoryWriter extends ServiceSupport implements Processor {
    private final static Logger logger = LoggerFactory.getLogger(AbstractHistoryWriter.class);

    @Value("${batch.id}")
    protected String batchId;

    @Autowired
    private DBConfigLoader configLoader;

    @Autowired
    private BatchHistoryRepository batchHistoryRepository;

    private BlockingQueue<UpdateEntity> queue = new LinkedBlockingQueue<>();



    private UpdateWorker updateWorker;

    private String prefix;

    protected AbstractHistoryWriter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        //다른 router에서 넘어온 경우는 null로 표시됨(https://camel.apache.org/maven/current/camel-core/apidocs/org/apache/camel/Exchange.html)
        String routerId = exchange.getFromRouteId();

        queue.add(new UpdateEntity(batchId, routerId));

    }

    @Override
    protected void doStart() throws Exception {
        updateWorker = new UpdateWorker();
        updateWorker.start();
    }
    @Override
    protected void doStop() throws Exception {

        if(updateWorker != null) {
            updateWorker.shutdown();

            while (updateWorker.isAlive()) {
                updateWorker.join();
                Thread.sleep(1000);
            }
        }

        try {
            //남아있는 데이터 commit
            commitAll();
        } catch (Exception e1) {
            //TODO: 에러 처리. camelcontext로 error를 전달할 경우 방안 추가.
        }
    }

    /**
     * list.add 와 동기화 필요하며 현재는 순차적으로 동작하기 때문에 별도 처리 하지 않음
     */
    @Transactional
    public void commit(BatchHistoryEntity entity) {

        batchHistoryRepository.save(entity);

        logger.info("{} commit : {}", prefix, entity.getBatchJobId());

    }



    @Transactional
    public void commitAll() throws InterruptedException {
        List<BatchHistoryEntity> list = new ArrayList<>();
        while(queue.size() > 0) {
            list.add(generate(queue.take()));
        }

        batchHistoryRepository.save(list);

        logger.info("{} commit all : {}", prefix, list.size());
    }

    class UpdateWorker extends Thread {
        private boolean isStart = true;

        public void run() {
            try {
                while (isStart) {
                    try {
                        UpdateEntity entity = queue.take();
                        if(entity != null) {
                            AbstractHistoryWriter.this.commit(generate(entity));
                        }
                    } catch (InterruptedException e) {
                        //empty
                    }
                }
            } catch (Exception e) {
                logger.error("error", e);
                //TODO: 에러 처리. camelcontext로 error를 전달할 경우 방안 추가.

            }

        }

        public void shutdown() {
            isStart = false;

            this.interrupt();
        }
    }

    protected abstract BatchHistoryEntity generate(UpdateEntity entity);


    class UpdateEntity {
        @Getter
        private final String batchId;
        @Getter
        private final String jobId;
        public UpdateEntity(String batchId, String jobId) {
            this.batchId = batchId;
            this.jobId = jobId;
        }
    }
}
