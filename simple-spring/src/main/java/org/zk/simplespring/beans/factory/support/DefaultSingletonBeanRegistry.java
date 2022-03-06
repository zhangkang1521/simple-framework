package org.zk.simplespring.beans.factory.support;

import org.zk.simplespring.beans.factory.DisposableBean;
import org.zk.simplespring.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例bean注册
 * @author zhangkang
 * @create 2022/2/27 20:37
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

	/** 单例bean */
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

	private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

	/**
	 * 从缓存中获取
	 * @param name
	 * @return
	 */
	@Override
	public Object getSingleton(String name) {
		return this.singletonObjects.get(name);
	}

	/**
	 * 加入缓存
	 * @param name
	 * @param bean
	 */
	public void addSingleton(String name, Object bean) {
		this.singletonObjects.put(name, bean);
	}

	public void registerDisposableBean(String beanName, DisposableBean bean) {
		disposableBeans.put(beanName, bean);
	}

	/**
	 * 实现了AbstractBeanFactory实现接口ConfigurableBeanFactory的方法
	 * 这里不能加@Override
	 */
	public void destroySingletons() {
		Set<String> keySet = this.disposableBeans.keySet();
		Object[] disposableBeanNames = keySet.toArray();

		for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
			Object beanName = disposableBeanNames[i];
			DisposableBean disposableBean = disposableBeans.remove(beanName);
			disposableBean.destroy();
		}
	}
}
