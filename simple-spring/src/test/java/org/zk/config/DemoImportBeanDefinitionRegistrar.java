package org.zk.config;

import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.context.annotation.ImportBeanDefinitionRegistrar;

public class DemoImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(DefaultListableBeanFactory defaultListableBeanFactory) {
		System.out.println("ok");
	}
}
