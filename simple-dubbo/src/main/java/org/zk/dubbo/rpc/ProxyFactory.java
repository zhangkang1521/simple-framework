package org.zk.dubbo.rpc;

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
    <T> T getProxy(ReferenceConfig<T> referenceConfig);
}
