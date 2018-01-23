package net.mj.camel.batch.db.jpa.entity;

import lombok.Data;
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

    @Column(name="IS_SINGLE_TRANSACTION")
    private boolean isSingleTransaction;

    @Column(name="READER_CLASS")
    private String readerClassName;

    @Column(name="READER_PROPERTIES")
    private String readerProperties = "";

    @Column(name="WORKER_CLASS")
    private String workerClassName;

    @Column(name="WORKER_PROPERTIES")
    private String workerProperties = "";

    @Column(name="WRITER_CLASS")
    private String writerClassName;

    @Column(name="WRITER_PROPERTIES")
    private String writerProperties = "";

    @Column(name="IS_USAGE")
    private boolean isUsage = true;

    @Column(name="REGIST_DATETIME")
    private Date registDatetime = new Date();

    @Column(name="UPDATE_DATETIME")
    private Date updateDateTime = new Date();

    @Column(name="START_DATETIME")
    private Date startDatetime = new Date();

    @Column(name="END_DATETIME")
    private Date endDateTime = new Date();

    @Column(name="EXECUTE_RESULT")
    private String executeResult;

    @Column(name="ERROR_MESSAGE")
    private String errorMessage;

    @ManyToOne
    @JoinColumn(name = "BATCH_ID", insertable = false, updatable = false)
    private BatchEntity batchEntity;


}
