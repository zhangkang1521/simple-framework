package org.zk.simplespring.beans.factory.xml;

import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

public class XmlBeanFactory extends DefaultListableBeanFactory {

	private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);

	public XmlBeanFactory(String resource) {
		this.reader.loadBeanDefinition(resource);
	}

}
