package net.mj.camel.batch.db.jpa.repository;

import net.mj.camel.batch.db.jpa.entity.BatchJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchJobRepository extends JpaRepository<BatchJobEntity, String>, BatchJobRepositoryCustom {

}
