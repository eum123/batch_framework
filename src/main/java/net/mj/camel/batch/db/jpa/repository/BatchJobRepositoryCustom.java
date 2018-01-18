package net.mj.camel.batch.db.jpa.repository;

import net.mj.camel.batch.db.jpa.entity.BatchJobEntity;

import java.util.List;

public interface BatchJobRepositoryCustom {

    List<BatchJobEntity> findAllByBatchId(String batchId);
}
