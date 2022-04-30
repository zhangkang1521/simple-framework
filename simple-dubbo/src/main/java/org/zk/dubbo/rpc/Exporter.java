package org.zk.dubbo.rpc;

/**
 * 暴露
 * @param <T>
 */
public interface Exporter<T> {

    Invoker<T> getInvoker();
}
