package org.zk.simplespring.beans.factory.xml;

import org.w3c.dom.Element;
import org.zk.simplespring.BeanDefinition;
import org.zk.simplespring.DefaultListableBeanFactory;

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
	BeanDefinition parse(Element element, DefaultListableBeanFactory beanFactory);
}
