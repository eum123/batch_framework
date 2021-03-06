package net.mj.camel.batch.loader;

import lombok.Setter;
import net.mj.camel.batch.db.jpa.entity.BatchJobEntity;
import net.mj.camel.batch.db.jpa.repository.BatchJobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * DB에서 Batch 설정 정보를 읽는다.
 *
 */
@ManagedResource(objectName = "batch:name=DBConfigLoader")
@Component("dbConfigLoader")
public class DBConfigLoader {
    private final static Logger logger = LoggerFactory.getLogger(DBConfigLoader.class);

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private Map<String, JobConfig> configs = new HashMap<String, JobConfig>();

    @Autowired
    private BatchJobRepository batchJobRepository;

    @Setter
    @Value("${batch.id}")
    private String batchId;

    private boolean isUpdate = false;

    @Transactional
    public void excuteQuery() throws Exception {

        List<BatchJobEntity> temp = batchJobRepository.findAllByBatchId(batchId);

        try {

            lock.lock();

            isUpdate = true;

            this.configs.clear();

            temp.forEach(entity -> {
                JobConfig config = new JobConfig();
                config.setJobId(entity.getBatchJobId());
                config.setWriterProperties(entity.getWriterProperties());
                config.setWriterBeanId(entity.getWriterBeanId());
                config.setWorkerProperties(entity.getWorkerProperties());
                config.setWorkerBeanId(entity.getWorkerBeanId());
                config.setReaderProperties(entity.getReaderProperties());
                config.setReaderBeanId(entity.getReaderBeanId());
                config.setSingleTransaction(entity.isSingleTransaction());
                config.setSchedule(entity.getJobSchedule());
                config.setUpdateDateTime(entity.getUpdateDateTime().getTime());

                configs.put(entity.getBatchJobId(), config);

                logger.info("add job config : " + config);
            });

        } finally {
            isUpdate = false;
            condition.signalAll();
            lock.unlock();
        }
    }

    public Map<String, JobConfig> getConfigs()  throws InterruptedException{
        try {
            lock.lock();
            if(isUpdate) {
                condition.await();
            }
            return new HashMap(configs);
        } finally {
            lock.unlock();
        }
    }

    public JobConfig getConfig(String batchJobId)  throws InterruptedException{
        try {
            lock.lock();
            if(isUpdate) {
                condition.await();
            }
            return configs.get(batchJobId);
        } finally {
            lock.unlock();
        }
    }


    /**
     * schedule로 등록되어 있음
     * @throws Exception
     */
    @ManagedOperation
    public void update() throws Exception {
        excuteQuery();
    }
}
