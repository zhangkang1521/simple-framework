package org.zk.simplespring.beans.factory.config;

import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

/**
 * 容器注册后处理器
 */
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {

	void postProcessBeanDefinitionRegistry(DefaultListableBeanFactory beanFactory);
}
