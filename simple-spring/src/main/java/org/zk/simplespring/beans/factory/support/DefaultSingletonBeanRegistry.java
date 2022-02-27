package org.zk.simplespring.beans.factory.support;

import org.zk.simplespring.beans.factory.config.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例bean注册
 * @author zhangkang
 * @create 2022/2/27 20:37
 */
public class DefaultSingletonBeanRegistry {

	/** 单例bean */
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

	/**
	 * 从缓存中获取
	 * @param name
	 * @return
	 */
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
}
