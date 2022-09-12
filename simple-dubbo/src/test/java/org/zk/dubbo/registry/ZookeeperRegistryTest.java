package org.zk.dubbo.registry;

import org.junit.Test;
import org.zk.dubbo.api.DemoService;
import org.zk.dubbo.common.URL;

import static org.junit.Assert.*;

public class ZookeeperRegistryTest {

    @Test
    public void register() {
        ZookeeperRegistry zookeeperRegistry = new ZookeeperRegistry();
        URL url = new URL("dubbo", "192.168.1.1", 20888, DemoService.class.getName());
        zookeeperRegistry.register(url);
    }
}