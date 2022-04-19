package org.zk.dubbo.config;

import lombok.Data;
import org.zk.dubbo.rpc.proxy.jdk.JdkProxyFactory;

/**
 * 客户端配置
 * @param <T>
 */
@Data
public class ReferenceConfig<T> {

    private String host;

    private Integer port;

//    private String url;

    private Class<T> interfaceClass;


    public T get() {
        return new JdkProxyFactory().getProxy(this);
    }
}
