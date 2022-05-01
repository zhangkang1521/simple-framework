package org.zk.dubbo.config;

import lombok.Data;
import org.zk.dubbo.common.URL;
import org.zk.dubbo.rpc.Invoker;
import org.zk.dubbo.rpc.Protocol;
import org.zk.dubbo.rpc.protocol.injvm.InjvmProtocol;
import org.zk.dubbo.rpc.proxy.jdk.JdkProxyFactory;

/**
 * 客户端配置
 * @param <T>
 */
@Data
public class ReferenceConfig<T> {

    private String url;

    private Class<T> interfaceClass;


    public T get() {
        // 1. 使用协议拿到Invoker
        URL url = URL.valueOf(this.url);
        url.setPath(interfaceClass.getName());
        // TODO
        Protocol protocol = new InjvmProtocol();
        Invoker<T> invoker = protocol.refer(interfaceClass, url);

        // 2. 创建代理
        return new JdkProxyFactory().getProxy(invoker);
    }
}
