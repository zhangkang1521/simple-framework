package org.zk.simplespring;

public interface BeanFactory {

	/**
	 * 按名称获取bean
	 * @param name
	 * @return
	 */
	Object getBean(String name);

	/**
	 * 按类型获取bean
	 * @param requiredType
	 * @param <T>
	 * @return
	 */
	<T> T getBean(Class<T> requiredType);
}
