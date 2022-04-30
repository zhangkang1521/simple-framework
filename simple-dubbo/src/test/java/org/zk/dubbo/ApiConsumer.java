package org.zk.dubbo;

import org.zk.dubbo.api.DemoService;
import org.zk.dubbo.config.ReferenceConfig;

public class ApiConsumer {

    public static void main(String[] args) throws Exception {
        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setUrl("dubbo://localhost:20888");
        referenceConfig.setInterfaceClass(DemoService.class);

        DemoService demoService = referenceConfig.get();

        String msg = demoService.sayHello("a");
        System.out.println("服务端返回：" + msg);

        String msg2 = demoService.sayHello("b");
        System.out.println("服务端返回：" + msg2);
//        System.in.read();
    }
}
