package net.mj.camel.batch.loader;


import lombok.Data;

@Data
public class JobConfig {
    private String jobId;
    private String schedule;
    private String processorId;
    private boolean isSingleTransaction = false;
    private String readerBeanId;
    private String readerProperties;
    private String workerBeanId;
    private String workerProperties;
    private String writerBeanId;
    private String writerProperties;

}
