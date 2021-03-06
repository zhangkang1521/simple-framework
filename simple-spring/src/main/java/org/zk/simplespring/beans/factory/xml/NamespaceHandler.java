package org.zk.simplespring.beans.factory.xml;

import org.w3c.dom.Element;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.support.BeanDefinitionRegistry;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

public interface NamespaceHandler {

	/**
	 * 在XmlBeanDefinition实例化NamespaceHandler后调用，注册parser
	 */
	void init();


	/**
	 * 解析自定义标签
	 * @param element
	 * @return
	 */
	BeanDefinition parse(Element element, BeanDefinitionRegistry registry);
}
