package org.zk.dubbo.rpc;

import org.zk.dubbo.common.URL;

/**
 * 协议
 */
public interface Protocol {

    /**
     * 客户端引用
     * @param type
     * @param url
     * @param <T>
     * @return
     */
    <T> Invoker<T> refer(Class<T> type, URL url);

    /**
     * 服务端暴露
     * @param invoker
     * @param <T>
     * @return
     */
    <T> Exporter<T> export(Invoker invoker);
}
