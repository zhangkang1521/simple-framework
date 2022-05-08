package org.zk.dubbo;

import org.junit.Test;
import org.zk.dubbo.api.DemoService;
import org.zk.dubbo.api.DemoServiceImpl;
import org.zk.dubbo.config.ReferenceConfig;
import org.zk.dubbo.config.ServiceConfig;

public class ApiTest {

    @Test
    public void testProvider() {
        ServiceConfig<DemoService> service = new ServiceConfig<DemoService>();
        service.setInterfaceClass(DemoService.class);
        service.setRef(new DemoServiceImpl());

        // 暴露服务
        service.export();
    }

    @Test
    public void testConsumer() throws Exception {
        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setUrl("dubbo://localhost:20888");
        referenceConfig.setInterfaceClass(DemoService.class);

        DemoService demoService = referenceConfig.get();

        String msg = demoService.sayHello("a");
        System.out.println("服务端返回：" + msg);

        String msg2 = demoService.sayHello("b");
        System.out.println("服务端返回：" + msg2);

        System.in.read();
    }

}
