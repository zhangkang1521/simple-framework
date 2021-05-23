package org.zk.simplespring.context.annotation;

import org.zk.simplespring.beans.SpringBeanUtils;
import org.zk.simplespring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

public class AnnotatedBeanDefinitionReader {

	private DefaultListableBeanFactory defaultListableBeanFactory;

	public AnnotatedBeanDefinitionReader(DefaultListableBeanFactory defaultListableBeanFactory) {
		this.defaultListableBeanFactory = defaultListableBeanFactory;

		BeanDefinition autowiredAnnotationBeanPostProcessor = new BeanDefinition();
		autowiredAnnotationBeanPostProcessor.setBeanClass(AutowiredAnnotationBeanPostProcessor.class);
		this.defaultListableBeanFactory.registerBeanDefinition("autowiredAnnotationBeanPostProcessor", autowiredAnnotationBeanPostProcessor);

		BeanDefinition configurationClassPostProcessor = new BeanDefinition();
		configurationClassPostProcessor.setBeanClass(ConfigurationClassPostProcessor.class);
		this.defaultListableBeanFactory.registerBeanDefinition("configurationClassPostProcessor", configurationClassPostProcessor);
	}

	public void register(Class<?> annotationClass) {
		// 注册启动配置类BeanDefinition
		BeanDefinition beanDefinition = new BeanDefinition();
		beanDefinition.setBeanClass(annotationClass);
		this.defaultListableBeanFactory.registerBeanDefinition(SpringBeanUtils.generateBeanName(annotationClass.getName()), beanDefinition);
	}
}
