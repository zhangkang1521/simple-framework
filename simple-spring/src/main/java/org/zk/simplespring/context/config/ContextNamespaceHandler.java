package org.zk.simplespring.context.config;

import org.w3c.dom.Element;
import org.zk.simplespring.BeanDefinition;
import org.zk.simplespring.DefaultListableBeanFactory;
import org.zk.simplespring.beans.factory.xml.BeanDefinitionParser;
import org.zk.simplespring.beans.factory.xml.NamespaceHandler;
import org.zk.simplespring.context.annotation.ComponentScanBeanDefinitionParser;

import java.util.HashMap;
import java.util.Map;

public class ContextNamespaceHandler implements NamespaceHandler {

	// TODO 提取到基类
	private final Map<String, BeanDefinitionParser> parsers = new HashMap<String, BeanDefinitionParser>();

	@Override
	public void init() {
		parsers.put("component-scan", new ComponentScanBeanDefinitionParser());
	}

	@Override
	public BeanDefinition parse(Element element, DefaultListableBeanFactory beanFactory) {
		String localName = element.getLocalName();
		BeanDefinitionParser parser = parsers.get(localName);
		return parser.parse(element, beanFactory);
	}
}
