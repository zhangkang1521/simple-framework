package org.zk.simplespring.context.config;

import org.zk.simplespring.beans.factory.xml.NamespaceHandlerSupport;
import org.zk.simplespring.context.annotation.ComponentScanBeanDefinitionParser;

public class ContextNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("component-scan", new ComponentScanBeanDefinitionParser());
	}

}
