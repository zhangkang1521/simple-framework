package org.zk.simplespring.beans.factory.config;

import org.zk.simplespring.DefaultListableBeanFactory;

/**
 * 容器后处理器
 */
public interface BeanFactoryPostProcessor {

	void postProcessBeanFactory(DefaultListableBeanFactory beanFactory);
}
