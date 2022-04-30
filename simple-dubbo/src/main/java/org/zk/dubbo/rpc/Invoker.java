package org.zk.dubbo.rpc;

import org.zk.dubbo.common.Node;

/**
 * Invoker，可对其发起调用，不同协议有不同实现
 *
 */
public interface Invoker<T> extends Node {

    /**
     * 发起调用
     * @param invocation
     * @return
     */
    Object invoke(RpcInvocation invocation);
}
