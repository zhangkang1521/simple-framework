package org.zk.simplespring.beans.factory.config;

public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);
}
