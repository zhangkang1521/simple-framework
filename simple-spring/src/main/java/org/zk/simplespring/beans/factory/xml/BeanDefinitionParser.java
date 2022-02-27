package org.zk.simplespring.beans.factory.xml;

import org.w3c.dom.Element;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.support.BeanDefinitionRegistry;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

public interface BeanDefinitionParser {

	BeanDefinition parse(Element element, BeanDefinitionRegistry registry);
}
