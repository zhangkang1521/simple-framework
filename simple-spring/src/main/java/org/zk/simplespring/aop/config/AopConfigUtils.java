package org.zk.simplespring.aop.config;

import org.zk.simplespring.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

public class AopConfigUtils {

	public static final String AUTO_PROXY_CREATOR_BEAN_NAME = "org.zk.simplespring.aop.config.internalAutoProxyCreator";

	public static void registerAutoProxyCreatorIfNecessory(DefaultListableBeanFactory beanFactory) {
		if (!beanFactory.getBeanDefinitionMap().containsKey(AUTO_PROXY_CREATOR_BEAN_NAME)) {
			BeanDefinition beanDefinition = new BeanDefinition();
			beanDefinition.setBeanClass(AnnotationAwareAspectJAutoProxyCreator.class);
			beanFactory.registerBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME, beanDefinition);
		}
	}
}
