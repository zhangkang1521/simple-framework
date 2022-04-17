package org.zk.dubbo.api;

public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String msg) {
        System.out.println("sayHello接口被调用");
        return "hello " +msg;
    }
}
