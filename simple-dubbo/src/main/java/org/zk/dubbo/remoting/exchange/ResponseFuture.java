package org.zk.dubbo.remoting.exchange;

/**
 * 返回结果
 */
public interface ResponseFuture {

    /**
     * 获取结果，结果未返回前一直等待
     * @return
     */
    Object get();

    /**
     * 结果是否返回
     * @return
     */
    // boolean isDone();
}
