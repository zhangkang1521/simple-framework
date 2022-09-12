package org.zk.dubbo;

import org.zk.dubbo.api.DemoService;
import org.zk.dubbo.api.DemoServiceImpl;
import org.zk.dubbo.config.RegistryConfig;
import org.zk.dubbo.config.ServiceConfig;

/**
 * TODO
 *
 * @author zhangkang
 * @date 2022/5/21 21:14
 */
public class ApiProviderTest {

    public static void main(String[] args) throws Exception {
        ServiceConfig<DemoService> service = new ServiceConfig<DemoService>();
        service.setInterfaceClass(DemoService.class);
        service.setRef(new DemoServiceImpl());
        service.setRegistry(new RegistryConfig("zookeeper://localhost:2181"));

        // 暴露服务
        service.export();

        System.in.read();
    }
}
