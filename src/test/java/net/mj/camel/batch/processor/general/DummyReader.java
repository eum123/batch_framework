package net.mj.camel.batch.processor.general;

import net.mj.camel.batch.processor.general.Reader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("dummyReader")
public class DummyReader implements Reader {
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
    public Object read() {
        return "my name is";
    }
}
