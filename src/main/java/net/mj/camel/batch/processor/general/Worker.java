package net.mj.camel.batch.processor.general;

import net.mj.camel.batch.processor.Executor;

import java.util.Map;

public interface Worker extends Executor {
    public Object work(Object readData);
}
