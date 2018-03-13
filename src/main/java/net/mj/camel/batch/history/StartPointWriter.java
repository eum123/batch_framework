package net.mj.camel.batch.history;


import net.mj.camel.batch.common.constants.BatchResultConstants;
import net.mj.camel.batch.db.jpa.entity.BatchHistoryEntity;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.support.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("startPointWriter")
public class StartPointWriter implements Processor {

    public static final String PREFIX = "START";

    @Autowired
    private HistoryUpdater historyUpdater;

    @Value("${batch.id}")
    protected String batchId;


    public StartPointWriter() {

    }


    @Override
    public void process(Exchange exchange) throws Exception {
        String exchangeId = exchange.getExchangeId();

        //다른 router에서 넘어온 경우는 null로 표시됨(https://camel.apache.org/maven/current/camel-core/apidocs/org/apache/camel/Exchange.html)
        String routerId = exchange.getFromRouteId();


        BatchHistoryEntity batchHistoryEntity = new BatchHistoryEntity();
        batchHistoryEntity.setSeq(exchangeId);
        batchHistoryEntity.setBatchId(batchId);
        batchHistoryEntity.setBatchJobId(routerId);
        batchHistoryEntity.setExecuteResult(String.valueOf(BatchResultConstants.DOING.getResultCode()));
        batchHistoryEntity.setErrorMessage("");

        Date nowDate = new Date();
        batchHistoryEntity.setStartDatetime(nowDate);
        batchHistoryEntity.setUpdateDateTime(nowDate);
        batchHistoryEntity.setRegistDatetime(nowDate);

        historyUpdater.process(batchHistoryEntity);
    }
}
