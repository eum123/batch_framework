package net.mj.camel.batch.history;


import net.mj.camel.batch.db.jpa.entity.BatchJobEntity;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StartPointWriter extends AbstractHistoryWriter implements Processor {

    public StartPointWriter() {
        super("StartPoint");
    }

    protected BatchJobEntity generate(UpdateEntity entity) {
        BatchJobEntity batchJobEntity = new BatchJobEntity();
        batchJobEntity.setBatchId(entity.getBatchId());
        batchJobEntity.setBatchJobId(entity.getJobId());
        batchJobEntity.setStartDatetime(new Date());

        return batchJobEntity;
    }


}
