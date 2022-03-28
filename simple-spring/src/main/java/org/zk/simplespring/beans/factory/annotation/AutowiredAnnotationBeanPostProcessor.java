package org.zk.simplespring.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.beans.PropertyValue;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.BeanFactoryAware;
import org.zk.simplespring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

import java.lang.reflect.Field;
import java.util.List;

public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

	public static final Logger log = LoggerFactory.getLogger(AutowiredAnnotationBeanPostProcessor.class);

	private DefaultListableBeanFactory beanFactory;

	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
		return null;
	}

	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) {
		return false;
	}

	@Override
	public List<PropertyValue> postProcessPropertyValues(List<PropertyValue> propertyValues, Object bean, String beanName) {

		for (Field field : bean.getClass().getDeclaredFields()) {
			// Autowired注解处理
			Autowired autowired = field.getAnnotation(Autowired.class);
			if (autowired != null) {
				log.debug("resolve [{}] @Autowired field [{}]", beanName, field.getName());
				Object dependency = beanFactory.getBean(field.getType());
				BeanUtil.setFieldValue(bean, field.getName(), dependency);
			}

			// @Value 注解处理
			Value value = field.getAnnotation(Value.class);
			if (value != null) {
				log.debug("resolve [{}] @Value field [{}]", beanName, field.getName());
				String resolvedValue = beanFactory.resolveEmbeddedValue(value.value());
				BeanUtil.setFieldValue(bean, field.getName(), resolvedValue);
			}
		}



		return propertyValues;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		return bean;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = (DefaultListableBeanFactory) beanFactory;
	}
}
