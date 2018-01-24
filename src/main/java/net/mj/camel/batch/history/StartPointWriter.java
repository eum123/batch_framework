package net.mj.camel.batch.history;


import net.mj.camel.batch.db.jpa.entity.BatchHistoryEntity;
import net.mj.camel.batch.db.jpa.entity.BatchJobEntity;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StartPointWriter extends AbstractHistoryWriter implements Processor {

    public StartPointWriter() {
        super("StartPoint");
    }

    protected BatchHistoryEntity generate(UpdateEntity entity) {
        BatchHistoryEntity batchHistoryEntity = new BatchHistoryEntity();
        batchHistoryEntity.setBatchId(entity.getBatchId());
        batchHistoryEntity.setBatchJobId(entity.getJobId());
        batchHistoryEntity.setStartDatetime(new Date());

        return batchHistoryEntity;
    }


}
