package org.zk.dubbo.rpc.protocol.injvm;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.zk.dubbo.api.DemoService;
import org.zk.dubbo.api.DemoServiceImpl;
import org.zk.dubbo.common.URL;
import org.zk.dubbo.rpc.Invoker;
import org.zk.dubbo.rpc.proxy.jdk.JdkProxyFactory;

@Slf4j
public class InjvmProtocolTest {

    @Test
    public void test() {
        URL url = new URL("injvm", "localhost", 0, DemoService.class.getName());

        DemoService demoService = new DemoServiceImpl();
        InjvmProtocol protocol = new InjvmProtocol();
        Invoker invoker = new JdkProxyFactory().getInvoker(demoService, DemoService.class, url);
        protocol.export(invoker);

        Invoker invoker1 = protocol.refer(DemoService.class, url);
        DemoService proxy = (DemoService)new JdkProxyFactory().getProxy(invoker1);

        String result = proxy.sayHello("zk");
        log.info("result:{}", result);
    }

}