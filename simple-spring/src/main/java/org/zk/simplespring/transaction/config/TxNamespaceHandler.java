package org.zk.simplespring.transaction.config;

import org.zk.simplespring.aop.config.AspectJAutoProxyBeanDefinitionParser;
import org.zk.simplespring.beans.factory.xml.NamespaceHandlerSupport;

/**
 * tx命名空间
 */
public class TxNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("annotation-driven", new AnnotationDrivenBeanDefinitionParser());
	}
}
