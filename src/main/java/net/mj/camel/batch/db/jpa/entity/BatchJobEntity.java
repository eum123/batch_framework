package net.mj.camel.batch.db.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Id;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="TB_BATCH_JOB")
public class BatchJobEntity implements Serializable {


    @Column(name = "BATCH_ID")
    private String batchId;

    @Id
    @Column(name = "BATCH_JOB_ID")
    private String batchJobId;

    @Column(name = "JOB_SCHEDULE")
    private String jobSchedule;

    @Column(name="PROCESSOR_ID")
    private String processorId;

    @Column(name="IS_SINGLE_TRANSACTION")
    private boolean isSingleTransaction;

    @Column(name="READER_BEAN_ID")
    private String readerBeanId;

    @Column(name="READER_PROPERTIES")
    private String readerProperties = "";

    @Column(name="WORKER_BEAN_ID")
    private String workerBeanId;

    @Column(name="WORKER_PROPERTIES")
    private String workerProperties = "";

    @Column(name="WRITER_BEAN_ID")
    private String writerBeanId;

    @Column(name="WRITER_PROPERTIES")
    private String writerProperties = "";

    @Column(name="IS_USAGE")
    private boolean isUsage = true;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REGIST_DATETIME")
    private Date registDatetime;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATE_DATETIME")
    private Date updateDateTime;

    @ManyToOne
    @JoinColumn(name = "BATCH_ID", insertable = false, updatable = false)
    private BatchEntity batchEntity;


}
