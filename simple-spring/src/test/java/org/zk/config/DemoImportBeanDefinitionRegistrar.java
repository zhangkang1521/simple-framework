package org.zk.config;

import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.context.annotation.ImportBeanDefinitionRegistrar;

public class DemoImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(Class<?> annotationClass, DefaultListableBeanFactory defaultListableBeanFactory) {
		EnableUser enableUser = annotationClass.getAnnotation(EnableUser.class);
		String userName = enableUser.value();
		System.out.println("ok");
	}
}
