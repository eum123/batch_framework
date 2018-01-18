package net.mj.camel.batch.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

/**
 * TODO: 외부 설정 파일을 읽는 기능.
 * <pre>
 * jvm option에서
 *    하나의 파일을 읽는 경우 : -Dexternal.config=./external_file.properties
 *    여러개의 파일을 읽는 경우 : -Dexternal.config=directory (여러 파일에 대해 구분할것인가 결정 필요)
 * </pre>
 */
public class ExternalPropertiesFileLoader implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    private final static Logger logger = LoggerFactory.getLogger(ExternalPropertiesFileLoader.class);
    private ResourceLoader loader = new DefaultResourceLoader();

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        logger.debug("load external properties file");
    }

}
