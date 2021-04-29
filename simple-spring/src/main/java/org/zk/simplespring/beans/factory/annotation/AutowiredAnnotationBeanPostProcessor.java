package org.zk.simplespring.beans.factory.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.beans.PropertyValue;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.BeanFactoryAware;
import org.zk.simplespring.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

	public static final Logger log = LoggerFactory.getLogger(AutowiredAnnotationBeanPostProcessor.class);

	private BeanFactory beanFactory;

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
		log.info("Autowired后置处理 {}", beanName);
		for (Field field : bean.getClass().getDeclaredFields()) {
			Annotation ann = field.getAnnotation(Autowired.class);
			if (ann != null) {
				field.setAccessible(true);
				log.debug("resolve [{}] dependency field [{}]", beanName, field.getName());
				//TODO FactoryBean这种有问题
				Object dependency = beanFactory.getBean(field.getType());
				try {
					field.set(bean, dependency);
					log.debug("Autowired bean [{}] set property [{}] value [{}]", beanName, field.getName(), dependency);
				} catch (IllegalAccessException e) {
					throw new RuntimeException("autowired set field exception", e);
				}
			}
		}
		return propertyValues;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		return null;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		return null;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
}
