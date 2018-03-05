package net.mj.camel.batch.router;

import net.mj.camel.batch.BatchLauncher;
import net.mj.camel.batch.db.jpa.entity.BatchEntity;
import net.mj.camel.batch.db.jpa.entity.BatchJobEntity;
import net.mj.camel.batch.db.jpa.repository.BatchJobRepository;
import net.mj.camel.batch.db.jpa.repository.BatchRepository;
import net.mj.camel.batch.loader.DBConfigLoader;
import net.mj.camel.batch.processor.general.*;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchLauncher.class)
@ActiveProfiles(profiles = "local")
@ImportResource("file:${batch.home}conf/batch-core.xml")
public class RouterTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Value("${batch.id}")
    private String batchId;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private BatchJobRepository batchJobRepository;

    @Autowired
    private DBConfigLoader loader;

    @Autowired
    private BatchRouteManager routeManager;

    @Bean(name="dummyReader")
    public Reader getReader() {
        return new DummyReader();
    }

    @Bean(name="dummyWriter")
    public Writer getWriter() {
        return new DummyWriter();
    }

    @Bean(name="dummyWorker")
    public Worker getWorker() {
        return new DummyWorker();
    }

    @Before
    public void init() throws Exception {



        BatchEntity entity = new BatchEntity();
        entity.setBatchId(batchId);
        entity.setBatchIp("127.0.0.1");
        entity.setBatchPort(6000);
        entity.setUsage(true);

        BatchJobEntity batchJobEntity = new BatchJobEntity();
        batchJobEntity.setSingleTransaction(false);
        batchJobEntity.setJobSchedule("0/5+*+*+*+*+?");
        batchJobEntity.setBatchId(batchId);
        batchJobEntity.setBatchJobId("job1");
        batchJobEntity.setReaderBeanId("dummyReader");
        batchJobEntity.setWorkerBeanId("dummyWorker");
        batchJobEntity.setWriterBeanId("dummyWriter");
        batchJobEntity.setProcessorId("general");
        batchJobEntity.setBatchEntity(entity);

        BatchJobEntity batchJobEntity1 = new BatchJobEntity();
        batchJobEntity1.setSingleTransaction(false);
        batchJobEntity1.setJobSchedule("0/5+*+*+*+*+?");
        batchJobEntity1.setBatchId(batchId);
        batchJobEntity1.setBatchJobId("job2");
        batchJobEntity1.setReaderBeanId("dummyReader");
        batchJobEntity1.setWorkerBeanId("dummyWorker");
        batchJobEntity1.setWriterBeanId("dummyWriter");
        batchJobEntity1.setProcessorId("general");
        batchJobEntity1.setBatchEntity(entity);


        batchRepository.saveAndFlush(entity);

        batchJobRepository.saveAndFlush(batchJobEntity);
        batchJobRepository.saveAndFlush(batchJobEntity1);

        //config를 실행한다
        loader.update();

    }

    @Test
    public void scope() throws Exception {

        System.out.println(routeManager);
        Thread.sleep(5000);

    }



    @After
    public void distroy() {
        batchJobRepository.deleteAll();
        batchRepository.deleteAll();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
