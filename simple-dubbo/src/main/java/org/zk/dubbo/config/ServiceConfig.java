package org.zk.dubbo.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.zk.dubbo.common.URL;
import org.zk.dubbo.registry.integration.RegistryProtocol;
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
public class ServiceConfig<T> extends AbstractInterfaceConfig {

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

        // 2.通过协议进行暴露
        if (getRegistry() != null) {
            // 有注册中心
            // TODO dubbo这里会使用注册协议包装dubbo协议，这里先简单处理下
            Invoker<?> invoker = proxyFactory.getInvoker(ref, (Class) interfaceClass, url);
            Protocol protocol = new RegistryProtocol(new DubboProtocol());
            protocol.export(invoker);
        } else {
            // 无注册中心
            Invoker<?> invoker = proxyFactory.getInvoker(ref, (Class) interfaceClass, url);
            Protocol protocol = new DubboProtocol();
            protocol.export(invoker);
        }
    }
}
