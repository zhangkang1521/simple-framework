package org.zk.dubbo;

import org.zk.dubbo.api.DemoService;
import org.zk.dubbo.api.DemoServiceImpl;
import org.zk.dubbo.config.ServiceConfig;

public class ApiProvider {

    public static void main(String[] args) {
        ServiceConfig<DemoService> service = new ServiceConfig<DemoService>();
        service.setInterfaceClass(DemoService.class);
        service.setRef(new DemoServiceImpl());

        // 暴露服务
        service.export();
    }
}
