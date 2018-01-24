package net.mj.camel.batch.history;


import net.mj.camel.batch.db.jpa.entity.BatchHistoryEntity;
import net.mj.camel.batch.db.jpa.entity.BatchJobEntity;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EndPointWriter extends AbstractHistoryWriter implements Processor {


    public EndPointWriter() {
        super("EndPoint");
    }


    protected BatchHistoryEntity generate(UpdateEntity entity) {
        BatchHistoryEntity batchHistoryEntity = new BatchHistoryEntity();
        batchHistoryEntity.setBatchId(entity.getBatchId());
        batchHistoryEntity.setBatchJobId(entity.getJobId());
        batchHistoryEntity.setEndDateTime(new Date());

        return batchHistoryEntity;
    }


}
