package org.zk.simplespring.context.support;

import org.zk.simplespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.zk.simplespring.core.io.ClassPathResource;

//public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext {
//
//	private String configLocation;
//
//	public AbstractRefreshableConfigApplicationContext() {
//		this.configLocation = configLocation;
//		this.refresh();
//	}
//
//	@Override
//	protected void obtainFreshBeanFactory() {
//		super.obtainFreshBeanFactory();
//		// 读取xml配置，加载beanDefinition
//		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
//		reader.loadBeanDefinitions(new ClassPathResource(this.configLocation));
//	}
//}
