package org.zk.simplespring.beans.factory;

public interface DisposableBean {

    /**
     * bean销毁方法
     */
    void destroy();
}
