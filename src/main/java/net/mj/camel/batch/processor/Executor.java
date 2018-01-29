package net.mj.camel.batch.processor;

import java.util.Map;

public interface Executor {
    public void setProperties(Map properties);

    public void start() throws Exception;

    public void stop() throws Exception;
}
