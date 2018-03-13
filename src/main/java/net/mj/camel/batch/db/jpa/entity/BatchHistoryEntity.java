package net.mj.camel.batch.db.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;



import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
@Table(name="TB_BATCH_HISTORY")
public class BatchHistoryEntity implements Serializable {

    @Id
    @Column(name="SEQ")
    private String seq;

    @Column(name = "BATCH_ID")
    private String batchId;

    @Column(name = "BATCH_JOB_ID")
    private String batchJobId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REGIST_DATETIME")
    private Date registDatetime;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATE_DATETIME")
    private Date updateDateTime;

    @CreationTimestamp
    @Column(name="START_DATETIME")
    private Date startDatetime;

    @UpdateTimestamp
    @Column(name="END_DATETIME")
    private Date endDateTime;

    @Column(name="EXECUTE_RESULT")
    private String executeResult;

    @Column(name="ERROR_MESSAGE")
    private String errorMessage;



}
