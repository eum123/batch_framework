package net.mj.camel.batch.processor.general;

import net.mj.camel.batch.processor.Executor;

import java.util.Map;

public interface Reader extends Executor {
    public Object read();
}
