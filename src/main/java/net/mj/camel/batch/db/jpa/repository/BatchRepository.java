package net.mj.camel.batch.db.jpa.repository;

import net.mj.camel.batch.db.jpa.entity.BatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<BatchEntity, String>{
}
