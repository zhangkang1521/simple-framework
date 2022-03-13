package org.zk.simplespring.beans.factory.config;

public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

    /**
     * 注册单例bean
     * @param beanName
     * @param singletonObject
     */
    void registerSingleton(String beanName, Object singletonObject);
}
