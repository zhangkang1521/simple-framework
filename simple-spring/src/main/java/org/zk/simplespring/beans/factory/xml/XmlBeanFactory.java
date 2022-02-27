package org.zk.simplespring.beans.factory.xml;

import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.core.io.Resource;

public class XmlBeanFactory extends DefaultListableBeanFactory {

	private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);

	public XmlBeanFactory(Resource resource) {
		this.reader.loadBeanDefinition(resource);
	}

}
