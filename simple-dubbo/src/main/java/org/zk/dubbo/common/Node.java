package org.zk.dubbo.common;

/**
 * Invoker,Registry,Directory,Monitor继承自该接口
 */
public interface Node {

    URL getUrl();
}
