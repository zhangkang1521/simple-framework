package org.zk.dubbo.rpc;

import org.zk.dubbo.common.URL;
import org.zk.dubbo.config.ReferenceConfig;

/**
 * 代理工厂
 */
public interface ProxyFactory {

    /**
     * 获取代理对象
     * @param referenceConfig
     * @param <T>
     * @return
     */
    <T> T getProxy(Invoker<T> invoker, ReferenceConfig<T> referenceConfig);


    /**
     * 服务端获取Invoker
     * @param ref
     * @param type
     * @param <T>
     * @return
     */
    <T> Invoker<T> getInvoker(T ref, Class<T> type, URL url);
}
