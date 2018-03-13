package net.mj.camel.batch.history;

import net.mj.camel.batch.db.jpa.entity.BatchHistoryEntity;
import net.mj.camel.batch.db.jpa.repository.BatchHistoryRepository;
import net.mj.camel.batch.loader.DBConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component("HistoryUpdater")
public class HistoryUpdater implements InitializingBean, DisposableBean {
    private final static Logger logger = LoggerFactory.getLogger(HistoryUpdater.class);

    @Value("${batch.id}")
    protected String batchId;

    @Autowired
    private BatchHistoryRepository batchHistoryRepository;

    private BlockingQueue<BatchHistoryEntity> queue = new LinkedBlockingQueue<>();

    private UpdateWorker updateWorker;

    public void process(BatchHistoryEntity entity) throws Exception {

        queue.add(entity);

    }

    @Override
    public void destroy() throws Exception {
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

    @Override
    public void afterPropertiesSet() throws Exception {
        updateWorker = new UpdateWorker();
        updateWorker.start();
    }

    /**
     * list.add 와 동기화 필요하며 현재는 순차적으로 동작하기 때문에 별도 처리 하지 않음
     */
    @Transactional
    public void commit(BatchHistoryEntity entity) {


        BatchHistoryEntity existEntity = batchHistoryRepository.findOneBySeq(entity.getSeq());
        if(existEntity == null) {
            batchHistoryRepository.save(entity);
        } else {
            existEntity.setExecuteResult(entity.getExecuteResult());
            existEntity.setErrorMessage(entity.getErrorMessage());
            existEntity.setEndDateTime(entity.getEndDateTime());
            batchHistoryRepository.save(existEntity);
        }

        logger.info("committed : {} / {}", entity.getSeq(), entity.getExecuteResult());


    }


    public void commitAll() throws InterruptedException {
        int count = 0;
        while(queue.size() > 0) {
            commit(queue.take());
            count ++;
        }


        logger.info("committed all : {}", count);
    }



    class UpdateWorker extends Thread {
        private boolean isStart = true;

        public void run() {
            try {
                while (isStart) {
                    try {
                        BatchHistoryEntity entity = queue.take();
                        if(entity != null) {
                            HistoryUpdater.this.commit(entity);
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


}
