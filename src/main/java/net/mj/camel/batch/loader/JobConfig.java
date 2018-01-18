package net.mj.camel.batch.loader;


import lombok.Data;

@Data
public class JobConfig {
    private String jobId;
    private String schedule;
    private boolean isSingleTransaction = false;
    private String readerClassName;
    private String readerProperties;
    private String workerClassName;
    private String workerProperties;
    private String writerClassName;
    private String writerProperties;

}
