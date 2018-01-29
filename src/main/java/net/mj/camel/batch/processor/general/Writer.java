package net.mj.camel.batch.processor.general;

import net.mj.camel.batch.processor.Executor;

import java.util.Map;

public interface Writer extends Executor {
    public void write(Object processData);
}
