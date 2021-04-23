package org.zk.simplespring;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

	/**
	 * 实例化前调用
	 * @param beanClass
	 * @param beanName
	 * @return
	 */
	Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName);

	/**
	 * 实例化后调用
	 * @param bean
	 * @param beanName
	 * @return
	 */
	boolean postProcessAfterInstantiation(Object bean, String beanName);

	/**
	 * 依赖注入中，属性设置前调用
	 * @param bean
	 * @param beanName
	 * @return
	 */
	void postProcessPropertyValues(Object bean, String beanName);
}
