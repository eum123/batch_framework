package net.mj.camel.batch.db.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="TB_BATCH")
public class BatchEntity {
    @Id
    @Column(name="BATCH_ID")
    private String batchId;

    @Column(name="BATCH_IP")
    private String batchIp;

    @Column(name="BATCH_PORT")
    private int batchPort;

    @Column(name="IS_USAGE")
    private boolean isUsage;

    @Column(name="REGIST_DATETIME")
    private Date registDatetime = new Date();

    @Column(name="UPDATE_DATETIME")
    private Date updateDateTime = new Date();

    @OneToMany(mappedBy = "batchEntity")
    private List<BatchJobEntity> batchJobEntityList = new ArrayList();

    public void addBatchJobEntity(BatchJobEntity entity) {
        batchJobEntityList.add(entity);
    }

}
