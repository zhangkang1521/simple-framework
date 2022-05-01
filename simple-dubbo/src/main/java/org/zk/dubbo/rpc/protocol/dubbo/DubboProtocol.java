package org.zk.dubbo.rpc.protocol.dubbo;

import org.zk.dubbo.common.URL;
import org.zk.dubbo.remoting.transport.netty4.NettyServer;
import org.zk.dubbo.rpc.Exporter;
import org.zk.dubbo.rpc.Invoker;
import org.zk.dubbo.rpc.Protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DubboProtocol implements Protocol {

    /**
     * 存储所有暴露的服务
     */
    protected final Map<String, Exporter<?>> exporterMap = new ConcurrentHashMap<>();



    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) {
        return new DubboInvoker<>(type, url);
    }

    @Override
    public <T> Exporter<T> export(Invoker invoker) {
        Exporter<T> exporter = new DubboExporter<>(invoker);
        URL url = invoker.getUrl();
        exporterMap.put(url.getPath(), exporter);

        new NettyServer(url, exporterMap).start();
        return exporter;
    }
}
