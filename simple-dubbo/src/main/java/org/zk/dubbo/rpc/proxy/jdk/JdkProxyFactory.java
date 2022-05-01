package org.zk.dubbo.rpc.proxy.jdk;

import org.zk.dubbo.common.URL;
import org.zk.dubbo.rpc.Invoker;
import org.zk.dubbo.rpc.InvokerInvocationHandler;
import org.zk.dubbo.rpc.ProxyFactory;
import org.zk.dubbo.rpc.RpcInvocation;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK代理工厂
 */
public class JdkProxyFactory implements ProxyFactory {


    @Override
    public <T> T getProxy(Invoker<T> invoker) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{invoker.getInterface()},
                new InvokerInvocationHandler(invoker));
    }

    public <T> Invoker<T> getInvoker(T ref, Class<T> type, URL url) {
        return new Invoker<T>() {

            @Override
            public Object invoke(RpcInvocation invocation) {
                try {
                    Method method = ref.getClass().getDeclaredMethod(invocation.getMethodName(), invocation.getParameterTypes());
                    return method.invoke(ref, invocation.getValues());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public URL getUrl() {
                return url;
            }

            @Override
            public Class<T> getInterface() {
                return type;
            }
        };
    }
}
