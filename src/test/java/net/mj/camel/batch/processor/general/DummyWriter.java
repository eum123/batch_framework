package net.mj.camel.batch.processor.general;

import net.mj.camel.batch.processor.general.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("dummyWriter")
public class DummyWriter implements Writer {
    private final static Logger logger = LoggerFactory.getLogger(DummyWriter.class);
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
    public void write(Object processData) {
        logger.debug(">>>>>display : " + processData);
    }
}
