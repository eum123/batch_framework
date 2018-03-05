package net.mj.camel.batch.common.util.router;

import net.mj.camel.batch.loader.JobConfig;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Constructor;


public class RouteHelper {

    /**
     * Router를 생성한다
     * @return
     */
    public static RouteBuilder createRouter(ApplicationContext applicationContext, String routerClassName, JobConfig jobConfig) throws Exception {

        Class routerClass =  Class.forName(routerClassName);
        Constructor constructor = routerClass.getConstructor(ApplicationContext.class, JobConfig.class);
        return (RouteBuilder)constructor.newInstance(applicationContext, jobConfig);

    }

}
