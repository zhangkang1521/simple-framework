package org.zk.dubbo.registry.integration;

import org.zk.dubbo.common.URL;
import org.zk.dubbo.registry.ZookeeperRegistry;
import org.zk.dubbo.rpc.Exporter;
import org.zk.dubbo.rpc.Invoker;
import org.zk.dubbo.rpc.Protocol;

/**
 * 注册中心协议
 *
 * @author zhangkang
 * @date 2022/5/15 15:48
 */
public class RegistryProtocol implements Protocol {

    /**
     * 具体的协议，如DubboProtocol
     */
    private Protocol protocol;

    public RegistryProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) {
        return null;
    }

    @Override
    public <T> Exporter<T> export(Invoker invoker) {
        // Dubbo协议暴露
        Exporter<T> exporter = protocol.export(invoker);
        // 注册中心节点创建
        new ZookeeperRegistry().register(invoker.getUrl());
        return exporter;
    }
}
