package net.mj.camel.batch.processor.general;

import net.mj.camel.batch.BatchLauncher;
import net.mj.camel.batch.db.jpa.entity.BatchEntity;
import net.mj.camel.batch.db.jpa.entity.BatchJobEntity;
import net.mj.camel.batch.db.jpa.repository.BatchJobRepository;
import net.mj.camel.batch.db.jpa.repository.BatchRepository;
import net.mj.camel.batch.loader.DBConfigLoader;
import net.mj.camel.batch.processor.general.GeneralBatchProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchLauncher.class)
@ActiveProfiles(profiles = "local")
@ImportResource("file:${batch.home}conf/batch-core.xml")
public class GeneralBatchProcessorTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Value("${batch.id}")
    private String batchId;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private BatchJobRepository batchJobRepository;

    @Autowired
    private DBConfigLoader loader;

    @Before
    public void init() throws Exception {
        BatchEntity entity = new BatchEntity();
        entity.setBatchId(batchId);
        entity.setBatchIp("127.0.0.1");
        entity.setBatchPort(6000);
        entity.setUsage(true);

        BatchJobEntity batchJobEntity = new BatchJobEntity();
        batchJobEntity.setSingleTransaction(false);
        batchJobEntity.setJobSchedule("1 * * * * * *");
        batchJobEntity.setBatchId(batchId);
        batchJobEntity.setBatchJobId("job1");
        batchJobEntity.setReaderBeanId("dummyReader");
        batchJobEntity.setWorkerBeanId("dummyWorker");
        batchJobEntity.setWriterBeanId("dummyWriter");
        batchJobEntity.setProcessorId("general");
        batchJobEntity.setBatchEntity(entity);


        batchRepository.saveAndFlush(entity);

        batchJobRepository.saveAndFlush(batchJobEntity);

        //config를 실행한다
        loader.update();
    }

    @Test
    public void scope() throws Exception {

        CamelContext ctx = new DefaultCamelContext();

        try {
            Exchange exchange = new DefaultExchange(ctx);

            ctx.start();

            GeneralBatchProcessor processor1 = applicationContext.getBean(GeneralBatchProcessor.class);
            GeneralBatchProcessor processor2 = applicationContext.getBean(GeneralBatchProcessor.class);

            Assert.assertNotEquals(processor1.hashCode(), processor2.hashCode());


        } finally {
            ctx.stop();
        }


    }


    @Test
    public void test() throws Exception {

        CamelContext ctx = new DefaultCamelContext();

        try {
            Exchange exchange = new DefaultExchange(ctx);

            ctx.start();

            GeneralBatchProcessor processor = applicationContext.getBean(GeneralBatchProcessor.class);
            processor.setBatchJobId("job1");
            processor.start();

        } finally {
            ctx.stop();
        }


    }


    @Test
    public void config() throws Exception {

        Assert.assertEquals(loader.getConfigs().size(), 1);

        CamelContext ctx = new DefaultCamelContext();

        try {
            Exchange exchange = new DefaultExchange(ctx);

            ctx.start();

            GeneralBatchProcessor processor = applicationContext.getBean(GeneralBatchProcessor.class);
            processor.setBatchJobId("job1");
            processor.start();

            processor.process(exchange);
        } finally {
            ctx.stop();
        }


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
