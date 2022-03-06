package org.zk.simplespring.context;

import org.zk.simplespring.context.support.ApplicationContext;

public interface ConfigurableApplicationContext extends ApplicationContext {

    void refresh();

    /**
     * 关闭容器，清除
     */
    void close();

    /**
     * 注册关闭函数钩子
     */
    void registerShutdownHook();
}
