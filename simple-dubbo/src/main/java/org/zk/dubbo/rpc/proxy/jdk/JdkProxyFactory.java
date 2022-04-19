package org.zk.dubbo.rpc.proxy.jdk;

import org.zk.dubbo.config.ReferenceConfig;
import org.zk.dubbo.rpc.InvokerInvocationHandler;
import org.zk.dubbo.rpc.ProxyFactory;

import java.lang.reflect.Proxy;

/**
 * JDK代理工厂
 */
public class JdkProxyFactory implements ProxyFactory {


    @Override
    public <T> T getProxy(ReferenceConfig<T> referenceConfig) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{referenceConfig.getInterfaceClass()},
                new InvokerInvocationHandler(referenceConfig));
    }
}
