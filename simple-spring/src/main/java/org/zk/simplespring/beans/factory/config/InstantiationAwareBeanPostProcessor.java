package org.zk.simplespring.beans.factory.config;

import org.zk.simplespring.beans.PropertyValue;
import org.zk.simplespring.beans.factory.config.BeanPostProcessor;

import java.util.List;

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
	 * <p>应用：@Autowired属性注入 </p>
	 * @param propertyValues
	 * @param bean
	 * @param beanName
	 * @return
	 */
	List<PropertyValue> postProcessPropertyValues(List<PropertyValue> propertyValues, Object bean, String beanName);
}
