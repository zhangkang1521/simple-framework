package org.zk.dubbo.common;

import org.junit.Test;

import static org.junit.Assert.*;

public class URLTest {

    @Test
    public void valueOf() {
        URL url = URL.valueOf("dubbo://localhost:20881");
        assertEquals("dubbo", url.getProtocol());
        assertEquals("localhost", url.getHost());
        assertEquals(20881, url.getPort());
    }

    @Test
    public void toFullString() {
        URL url = URL.valueOf("registry://localhost:20881");
        url.setPath("org.zk.dubbo.registry.RegistryService");
        url.addParameter(Constants.EXPORT_KEY, "dubbo://localhost:20881/org.zk.DemoService");
        url.addParameter("test", "xx");
        // registry://localhost:20881/org.zk.dubbo.registry.RegistryService?test=xx&export=dubbo://localhost:20881/org.zk.DemoService
        System.out.println(url.toFullString());
    }
}