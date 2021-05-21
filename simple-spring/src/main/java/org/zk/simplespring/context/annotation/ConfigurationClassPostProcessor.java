package org.zk.simplespring.context.annotation;

import org.zk.simplespring.beans.factory.config.BeanDefinitionRegistryPostProcessor;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持@Configuration注解
 */
public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor {


	@Override
	public void postProcessBeanDefinitionRegistry(DefaultListableBeanFactory beanFactory) {
		// 找出@Configuration注解的BeanDefinition
		List<Class<?>> configurationClassList = new ArrayList<>();
		beanFactory.getBeanDefinitionMap().forEach((beanName, beanDefinition) -> {
			Class<?> clz = beanDefinition.resolveBeanClass();
			if (clz.getAnnotation(Configuration.class) != null) {
				configurationClassList.add(clz);
			}
		});
		// 解析类上支持的注解
		ConfigurationClassParser configurationClassParser = new ConfigurationClassParser(beanFactory);
		for (Class<?> clz : configurationClassList) {
			configurationClassParser.parse(clz);
		}
	}

	@Override
	public void postProcessBeanFactory(DefaultListableBeanFactory beanFactory) {

	}
}
