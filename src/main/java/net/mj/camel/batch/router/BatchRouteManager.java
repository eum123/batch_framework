package net.mj.camel.batch.router;

import net.mj.camel.batch.common.util.router.RouteHelper;
import net.mj.camel.batch.loader.DBConfigLoader;
import net.mj.camel.batch.loader.JobConfig;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;

@DependsOn("dbConfigLoader")
@Component("batchRouteManager")
@Lazy
public class BatchRouteManager implements ApplicationContextAware, InitializingBean, DisposableBean{
    private static final Logger log = LoggerFactory.getLogger(BatchRouteManager.class);

    private ApplicationContext applicationContext;
    private CamelContext camelContext;

    @Autowired
    private DBConfigLoader loader;

    @Value("${batch.id}")
    private String batchId;

    @Value("${batch.router}")
    private String routerClassName;

    @PreDestroy
    @Override
    public void destroy() throws Exception {
        if(camelContext != null) {
            camelContext.stop();
        }
    }


    public void update() throws Exception {

        for(Map.Entry<String, JobConfig> entry : loader.getConfigs().entrySet()) {
            RouteBuilder router = RouteHelper.createRouter(applicationContext, routerClassName, entry.getValue());

            camelContext.addRoutes(router);

            log.info("ADD router : {}", entry.getKey());
        }


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        camelContext = new DefaultCamelContext();

        camelContext.start();
    }
}
