package net.mj.camel.batch.router;

import net.mj.camel.batch.common.util.router.RouteHelper;
import net.mj.camel.batch.loader.DBConfigLoader;
import net.mj.camel.batch.loader.JobConfig;
import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.RouteDefinition;
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
import java.util.HashMap;
import java.util.Map;

@DependsOn("dbConfigLoader")
@Component("batchRouteManager")
@Lazy
public class BatchRouteManager implements ApplicationContextAware, InitializingBean, DisposableBean{
    private static final Logger log = LoggerFactory.getLogger(BatchRouteManager.class);

    private ApplicationContext applicationContext;
    private CamelContext camelContext;

    private Map<String, Long> registedRoutes = new HashMap<>();

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


    public synchronized void update() throws Exception {

        if(loader.getConfigs() == null) {
            log.info("batch config is empty");
            return;
        }

        for(Map.Entry<String, JobConfig> entry : loader.getConfigs().entrySet()) {

            Route route = camelContext.getRoute(entry.getValue().getJobId());

            if(route == null) {
                //regist route
                registRoute(entry.getValue());

            } else if(registedRoutes.containsKey(route.getId()) && registedRoutes.get(route.getId()) < entry.getValue().getUpdateDateTime()) {
                camelContext.removeRoute(route.getId());

                registRoute(entry.getValue());
            }


        }

    }
    private void registRoute(JobConfig config) throws Exception {
        RouteBuilder router = RouteHelper.createRouter(applicationContext, routerClassName, config);

        camelContext.addRoutes(router);

        registedRoutes.put(config.getJobId(), config.getUpdateDateTime());

        log.info("ADD router : {}", config.getJobId());
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
