package org.zk.simplespring.context;

import java.util.EventListener;

/**
 * 事件监听器
 * @param <E>
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /**
     * 执行事件
     * @param event
     */
    void onApplicationEvent(E event);
}
