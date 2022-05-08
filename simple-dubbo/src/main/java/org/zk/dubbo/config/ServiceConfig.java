package org.zk.dubbo.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.zk.dubbo.common.URL;
import org.zk.dubbo.rpc.Invoker;
import org.zk.dubbo.rpc.Protocol;
import org.zk.dubbo.rpc.ProxyFactory;
import org.zk.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.zk.dubbo.rpc.proxy.jdk.JdkProxyFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 服务端配置
 *
 * @param <T>
 */
@Data
@Slf4j
public class ServiceConfig<T> {

    /**
     * 接口
     */
    private Class<?> interfaceClass;

    /**
     * 实现
     */
    private T ref;

    /**
     * 服务暴露
     */
    private Map<String, Object> exporterMap = new ConcurrentHashMap<>();

    public void export() {
        log.info("暴露服务 {} -> {}", interfaceClass.getName(), ref);

        // 1. 拿到Invoker
        URL url = new URL("dubbo", "localhost", 20888, interfaceClass.getName());
        ProxyFactory proxyFactory = new JdkProxyFactory();
        Invoker<?> invoker = proxyFactory.getInvoker(ref, (Class) interfaceClass, url);

        // 2.通过协议进行暴露
        // TODO
        Protocol protocol = new DubboProtocol();
        protocol.export(invoker);
    }
}
