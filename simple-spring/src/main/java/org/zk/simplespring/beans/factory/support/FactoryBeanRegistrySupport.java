package org.zk.simplespring.beans.factory.support;

import org.zk.simplespring.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    /** 缓存FactoryBean getObject方法返回的对象 */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();



    /**
     * 获取FactoryBean getObject方法缓存的实例
     * @param beanName
     * @return
     */
    protected Object getCachedObjectForFactoryBean(String beanName) {
        return factoryBeanObjectCache.get(beanName);
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        if (object == null) {
            object = doGetObjectFromFactoryBean(factory, beanName);
            this.factoryBeanObjectCache.put(beanName, object);
        }
        return object;
    }

    private Object doGetObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
        return ((FactoryBean) factory).getObject();
    }

}
