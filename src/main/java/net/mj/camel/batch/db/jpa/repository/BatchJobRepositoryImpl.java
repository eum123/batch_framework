package net.mj.camel.batch.db.jpa.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import net.mj.camel.batch.db.jpa.entity.BatchJobEntity;
import net.mj.camel.batch.db.jpa.entity.QBatchEntity;
import net.mj.camel.batch.db.jpa.entity.QBatchJobEntity;
import net.mj.camel.batch.loader.DBConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BatchJobRepositoryImpl extends QueryDslRepositorySupport implements BatchJobRepositoryCustom {

    private final static Logger logger = LoggerFactory.getLogger(BatchJobRepositoryImpl.class);

    public BatchJobRepositoryImpl() {
        super(BatchJobEntity.class);
    }

    @Override
    public List<BatchJobEntity> findAllByBatchId(String batchId) {
        QBatchJobEntity batchJobEntity = QBatchJobEntity.batchJobEntity;
        QBatchEntity batchEntity = QBatchEntity.batchEntity;

        JPAQuery query = new JPAQuery<BatchJobEntity>(getEntityManager()).from(batchJobEntity);

        query.innerJoin(batchJobEntity.batchEntity(), batchEntity);

        //where
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(batchJobEntity.isUsage.eq(true));
        booleanBuilder.and(batchJobEntity.batchEntity().isUsage.eq(true));
        booleanBuilder.and(batchJobEntity.batchId.eq(batchId));
        query.where(booleanBuilder);

        return query.fetch();
    }
}
