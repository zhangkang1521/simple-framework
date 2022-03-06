package org.zk.simplespring.beans.factory;


import java.util.List;

/**
 * 对BeanFactory进行扩展，BeanFactory仅能通过beanName获取bean，
 * ListableBeanFactory可根据所有beanDefinitions列出所有bean或按类型获取bean等
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 按类型获取多个bean
     * @param requiredType
     * @param <T>
     * @return
     */
    <T> List<T> getBeanList(Class<T> requiredType);


    /**
     * 按类型获取单个bean，找到多个抛出异常
     * @param requiredType
     * @param <T>
     * @return
     */
    <T> T getBean(Class<T> requiredType);
}
