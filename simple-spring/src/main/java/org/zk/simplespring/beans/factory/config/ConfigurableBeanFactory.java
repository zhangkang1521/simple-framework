package org.zk.simplespring.beans.factory.config;

import org.zk.simplespring.beans.factory.BeanFactory;

/**
 * AbstractBeanFactory实现
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例对象
     */
    void destroySingletons();
}
