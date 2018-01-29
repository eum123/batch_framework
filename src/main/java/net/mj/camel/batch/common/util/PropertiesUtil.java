package net.mj.camel.batch.common.util;

import java.util.HashMap;
import java.util.Map;

public class PropertiesUtil {
    public static Map stringToMap(String propertiesString) {
        Map map = new HashMap();

        if(propertiesString.indexOf("&") > 0) {
            String[] keyValues = propertiesString.split("&");

            for(String keyValue : keyValues) {
                setProperty(map, keyValue);
            }
        } else {
            setProperty(map, propertiesString);
        }

        return map;
    }

    private static void setProperty(Map map, String keyValue) {
        int index = keyValue.indexOf("=");
        if(index > 0) {
            String key = keyValue.substring(0, index);
            String value = keyValue.substring(index + 1);

            map.put(key, value);
        }
    }
}
