package org.zk.simplespring.beans.factory.xml;

import org.w3c.dom.Element;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class NamespaceHandlerSupport implements NamespaceHandler {

	private final Map<String, BeanDefinitionParser> parsers = new HashMap<String, BeanDefinitionParser>();

	protected final void registerBeanDefinitionParser(String elementName, BeanDefinitionParser parser) {
		this.parsers.put(elementName, parser);
	}

	@Override
	public BeanDefinition parse(Element element, DefaultListableBeanFactory beanFactory) {
		String localName = element.getLocalName();
		BeanDefinitionParser parser = parsers.get(localName);
		return parser.parse(element, beanFactory);
	}
}
