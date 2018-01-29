package net.mj.camel.batch.loader;

import net.mj.camel.batch.BatchLauncher;
import net.mj.camel.batch.db.jpa.entity.BatchEntity;
import net.mj.camel.batch.db.jpa.entity.BatchJobEntity;
import net.mj.camel.batch.db.jpa.repository.BatchJobRepository;
import net.mj.camel.batch.db.jpa.repository.BatchRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchLauncher.class)
@ActiveProfiles(profiles = "local")
@ImportResource("file:${batch.home}conf/batch-core.xml")
public class DBConfigLoaderTest {
    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private BatchJobRepository batchJobRepository;

    @Autowired
    private DBConfigLoader loader;

    @Test
    public void empty() throws Exception {

        batchRepository.deleteAll();

        Map<String, JobConfig> list = loader.getConfigs();

        Assert.assertEquals(list.size(), 0);


    }

    @Test
    public void selectOne() throws Exception {
        BatchEntity entity = new BatchEntity();
        entity.setBatchId("test1");
        entity.setBatchIp("127.0.0.1");
        entity.setBatchPort(6000);
        entity.setUsage(true);

        BatchJobEntity batchJobEntity = new BatchJobEntity();
        batchJobEntity.setSingleTransaction(false);
        batchJobEntity.setJobSchedule("1 * * * * * *");
        batchJobEntity.setBatchId("test1");
        batchJobEntity.setBatchJobId("job1");
        batchJobEntity.setReaderBeanId("");
        batchJobEntity.setWorkerBeanId("");
        batchJobEntity.setWriterBeanId("");
        batchJobEntity.setProcessorId("general");
        batchJobEntity.setBatchEntity(entity);


        batchRepository.save(entity);

        batchJobRepository.save(batchJobEntity);


        loader.setBatchId("test1");
        loader.update();


        Assert.assertEquals(loader.getConfigs().size(), 1);


        batchJobRepository.deleteAll();
        batchRepository.deleteAll();
    }


    @Test
    public void isUsageInTB_BATCH() throws Exception {
        BatchEntity entity = new BatchEntity();
        entity.setBatchId("test1");
        entity.setBatchIp("127.0.0.1");
        entity.setBatchPort(6000);
        entity.setUsage(false);

        BatchJobEntity batchJobEntity = new BatchJobEntity();
        batchJobEntity.setSingleTransaction(false);
        batchJobEntity.setJobSchedule("1 * * * * * *");
        batchJobEntity.setBatchId("test1");
        batchJobEntity.setBatchJobId("job1");
        batchJobEntity.setReaderBeanId("");
        batchJobEntity.setWorkerBeanId("");
        batchJobEntity.setWriterBeanId("");
        batchJobEntity.setBatchEntity(entity);


        batchRepository.save(entity);

        batchJobRepository.save(batchJobEntity);


        loader.setBatchId("test1");
        loader.update();


        Assert.assertEquals(loader.getConfigs().size(), 0);


        batchJobRepository.deleteAll();
        batchRepository.deleteAll();
    }

    @Test
    public void isUsageInTB_BATCH_JOB() throws Exception {
        BatchEntity entity = new BatchEntity();
        entity.setBatchId("test1");
        entity.setBatchIp("127.0.0.1");
        entity.setBatchPort(6000);
        entity.setUsage(true);

        BatchJobEntity batchJobEntity = new BatchJobEntity();
        batchJobEntity.setSingleTransaction(false);
        batchJobEntity.setJobSchedule("1 * * * * * *");
        batchJobEntity.setBatchId("test1");
        batchJobEntity.setBatchJobId("job1");
        batchJobEntity.setReaderBeanId("");
        batchJobEntity.setWorkerBeanId("");
        batchJobEntity.setWriterBeanId("");
        batchJobEntity.setBatchEntity(entity);
        batchJobEntity.setUsage(false);


        batchRepository.save(entity);

        batchJobRepository.save(batchJobEntity);


        loader.setBatchId("test1");
        loader.update();


        Assert.assertEquals(loader.getConfigs().size(), 0);


        batchJobRepository.deleteAll();
        batchRepository.deleteAll();
    }

    @Test
    public void differenceJobId() throws Exception {
        BatchEntity entity = new BatchEntity();
        entity.setBatchId("test1");
        entity.setBatchIp("127.0.0.1");
        entity.setBatchPort(6000);
        entity.setUsage(true);

        BatchJobEntity batchJobEntity = new BatchJobEntity();
        batchJobEntity.setSingleTransaction(false);
        batchJobEntity.setJobSchedule("1 * * * * * *");
        batchJobEntity.setBatchId("test1");
        batchJobEntity.setBatchJobId("job1");
        batchJobEntity.setReaderBeanId("");
        batchJobEntity.setWorkerBeanId("");
        batchJobEntity.setWriterBeanId("");
        batchJobEntity.setBatchEntity(entity);
        batchJobEntity.setUsage(false);


        batchRepository.save(entity);

        batchJobRepository.save(batchJobEntity);


        loader.setBatchId("test0");
        loader.update();


        Assert.assertEquals(loader.getConfigs().size(), 0);


        batchJobRepository.deleteAll();
        batchRepository.deleteAll();
    }
}
