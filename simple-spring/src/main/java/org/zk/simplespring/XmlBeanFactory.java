package org.zk.simplespring;

public class XmlBeanFactory extends DefaultListableBeanFactory {

	private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);

	public XmlBeanFactory(String resource) {
		this.reader.loadBeanDefinition(resource);
	}

}
