package net.mj.camel.batch.db.jpa.repository;

import net.mj.camel.batch.db.jpa.entity.BatchHistoryEntity;
import net.mj.camel.batch.db.jpa.entity.BatchJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchHistoryRepository extends JpaRepository<BatchHistoryEntity, String> {
    public BatchHistoryEntity findOneBySeq(String seq);
}
