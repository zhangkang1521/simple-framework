package org.zk.dubbo.rpc.protocol.injvm;

import org.zk.dubbo.common.URL;
import org.zk.dubbo.rpc.Exporter;
import org.zk.dubbo.rpc.Invoker;
import org.zk.dubbo.rpc.Protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 同一虚拟机调用协议
 */
public class InjvmProtocol implements Protocol {

    /**
     * 存储所有暴露的服务
     */
    protected final Map<String, Exporter<?>> exporterMap = new ConcurrentHashMap<>();



    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) {
        return new InjvmInvoker<>(type, url, exporterMap);
    }

    @Override
    public <T> Exporter<T> export(Invoker invoker) {

        Exporter<T> exporter = new InjvmExporter<>(invoker);
        URL url = invoker.getUrl();
        exporterMap.put(url.getPath(), exporter);

        return exporter;
    }
}
