package net.mj.camel.batch.common.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class PropertiesUtilTest {

    @Test
    public void empty() {
        String propertiesString = "";

        Map map = PropertiesUtil.stringToMap(propertiesString);

        Assert.assertEquals(map.size(), 0);
    }

    @Test
    public void single() {
        String propertiesString = "a=1";

        Map map = PropertiesUtil.stringToMap(propertiesString);

        Assert.assertEquals(map.size(), 1);

        Assert.assertEquals(map.get("a"), "1");
    }

    @Test
    public void multi() {
        String propertiesString = "a=1&b=2&c=3";

        Map map = PropertiesUtil.stringToMap(propertiesString);

        Assert.assertEquals(map.size(), 3);

        Assert.assertEquals(map.get("a"), "1");
        Assert.assertEquals(map.get("b"), "2");
        Assert.assertEquals(map.get("c"), "3");
    }
}
