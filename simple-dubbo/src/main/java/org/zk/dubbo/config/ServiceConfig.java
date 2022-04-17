package org.zk.dubbo.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.zk.dubbo.remoting.transport.netty4.NettyServer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 服务端配置
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
        // Invoker<?> invoker = proxyFactory.getInvoker(ref, (Class) interfaceClass);
        // Exporter<?> exporter = protocol.export(wrapperInvoker);
        log.info("暴露服务 {} -> {}", interfaceClass.getName(), ref);
        this.exporterMap.put(interfaceClass.getName(), ref);
        new NettyServer(exporterMap).start();
    }
}
