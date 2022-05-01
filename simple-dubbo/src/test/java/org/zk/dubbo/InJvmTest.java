package org.zk.dubbo;

import org.zk.dubbo.api.DemoService;
import org.zk.dubbo.api.DemoServiceImpl;
import org.zk.dubbo.config.ReferenceConfig;
import org.zk.dubbo.config.ServiceConfig;

public class InJvmTest {

    public static void main(String[] args) throws Exception {

        // TODO 2个地方都new InJvmProtocol，如何公用
        // 服务端
        ServiceConfig<DemoService> service = new ServiceConfig<DemoService>();
        service.setInterfaceClass(DemoService.class);
        service.setRef(new DemoServiceImpl());
        service.export();

        // 客户端
        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setUrl("injvm://localhost:20888");
        referenceConfig.setInterfaceClass(DemoService.class);
        DemoService demoService = referenceConfig.get();

        String msg = demoService.sayHello("a");
        System.out.println("服务端返回：" + msg);
    }
}
