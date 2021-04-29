package org.zk.simplespring.beans.factory.xml;

import org.w3c.dom.Element;
import org.zk.simplespring.BeanDefinition;
import org.zk.simplespring.DefaultListableBeanFactory;

public interface BeanDefinitionParser {

	BeanDefinition parse(Element element, DefaultListableBeanFactory beanFactory);
}
