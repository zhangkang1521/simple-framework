package org.zk.simplespring.aop.config;

import org.zk.simplespring.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.support.BeanDefinitionRegistry;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

public class AopConfigUtils {

	public static final String AUTO_PROXY_CREATOR_BEAN_NAME = "org.zk.simplespring.aop.config.internalAutoProxyCreator";

	public static void registerAutoProxyCreatorIfNecessory(BeanDefinitionRegistry registry) {
		if (!registry.containsBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME)) {
			BeanDefinition beanDefinition = new BeanDefinition();
			beanDefinition.setBeanClass(AnnotationAwareAspectJAutoProxyCreator.class);
			registry.registerBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME, beanDefinition);
		}
	}
}
