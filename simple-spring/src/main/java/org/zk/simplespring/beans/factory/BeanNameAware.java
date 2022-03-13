package org.zk.simplespring.beans.factory;

public interface BeanNameAware extends Aware {

    void setBeanName(String beanName);
}
