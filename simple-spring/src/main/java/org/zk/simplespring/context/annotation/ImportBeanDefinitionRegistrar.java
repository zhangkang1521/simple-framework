package org.zk.simplespring.context.annotation;

import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

/**
 * Import注解导入的配置类实现改接口,用于注册BeanDefinition
 */
public interface ImportBeanDefinitionRegistrar {

	void registerBeanDefinitions(DefaultListableBeanFactory defaultListableBeanFactory);
}
