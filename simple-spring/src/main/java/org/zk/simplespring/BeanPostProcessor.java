package org.zk.simplespring;

public interface BeanPostProcessor {

	/**
	 * bean初始化前调用
	 * @param bean
	 * @param beanName
	 * @return
	 */
	Object postProcessBeforeInitialization(Object bean, String beanName);

	/**
	 * bean初始化后调用
	 * @param bean
	 * @param beanName
	 * @return
	 */
	Object postProcessAfterInitialization(Object bean, String beanName);
}
