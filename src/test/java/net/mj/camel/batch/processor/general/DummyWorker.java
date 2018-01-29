package net.mj.camel.batch.processor.general;

import net.mj.camel.batch.processor.general.Worker;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("dummyWorker")
public class DummyWorker implements Worker {
    @Override
    public void setProperties(Map properties) {

    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {

    }

    @Override
    public Object work(Object readData) {
        return (String)readData + " java";
    }
}
