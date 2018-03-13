package net.mj.camel.batch.db.jpa.repository;

import net.mj.camel.batch.BatchLauncher;
import net.mj.camel.batch.db.jpa.entity.BatchHistoryEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchLauncher.class)
@ActiveProfiles(profiles = "local")
@ImportResource("file:${batch.home}conf/batch-core.xml")
public class BatchHistoryRepositoryTest {

    @Autowired
    private BatchHistoryRepository repository;

    @Test
    public void saveTest() throws Exception {


        try {
            BatchHistoryEntity entity = new BatchHistoryEntity();

            entity.setBatchId("batchId");
            entity.setBatchJobId("batchJobId");

            repository.save(entity);

            Assert.assertEquals(1, repository.count());


        } finally {
            repository.deleteAll();
        }
    }
}
