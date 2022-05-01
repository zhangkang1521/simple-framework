package org.zk.dubbo.rpc;

import org.zk.dubbo.common.URL;

/**
 * 代理工厂
 */
public interface ProxyFactory {

    /**
     * 获取代理对象
     * @param interfaces
     * @param <T>
     * @return
     */
    <T> T getProxy(Invoker<T> invoker);


    /**
     * 服务端获取Invoker
     * @param ref
     * @param type
     * @param <T>
     * @return
     */
    <T> Invoker<T> getInvoker(T ref, Class<T> type, URL url);
}
