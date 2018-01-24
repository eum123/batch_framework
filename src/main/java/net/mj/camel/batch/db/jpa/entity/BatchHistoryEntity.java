package net.mj.camel.batch.db.jpa.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="TB_BATCH_HISTORY")
public class BatchHistoryEntity implements Serializable {

    @Id
    @Column(name="SEQ")
    @GeneratedValue()
    private Long seq;

    @Column(name = "BATCH_ID")
    private String batchId;

    @Column(name = "BATCH_JOB_ID")
    private String batchJobId;

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



}
