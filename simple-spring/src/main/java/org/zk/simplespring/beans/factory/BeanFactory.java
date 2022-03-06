package org.zk.simplespring.beans.factory;

import java.util.List;

public interface BeanFactory {


	/**
	 * beanName加上这个前缀会返回工厂的实例，不会调用getObject返回应用中需要的实例
	 */
	String FACTORY_BEAN_PREFIX = "&";

	/**
	 * 按名称获取bean
	 * @param name
	 * @return
	 */
	Object getBean(String name);



}
