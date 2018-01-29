package net.mj.camel.batch.common.util;


import net.mj.camel.batch.processor.Executor;

public class ObjectUtil {
    /**
     * Executor를 생성한다
     * @param className 생성할 class name
     * @param properties 설정값
     * @return Executor
     * @throws Exception
     */
    public static <T extends Executor> T createExecutor(String className, String properties, Class<T> returnClass) throws Exception {

        Executor executor = (Executor)Class.forName(className).newInstance();
        executor.setProperties(PropertiesUtil.stringToMap(properties));

        return returnClass.cast(executor);
    }
}
