package org.zk.simplespring.aop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.zk.simplespring.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.beans.factory.xml.BeanDefinitionParser;

public class AspectJAutoProxyBeanDefinitionParser implements BeanDefinitionParser {

	public static final Logger log = LoggerFactory.getLogger(AspectJAutoProxyBeanDefinitionParser.class);

	public static final String AUTO_PROXY_CREATOR_BEAN_NAME = "org.zk.simplespring.aop.config.internalAutoProxyCreator";

	@Override
	public BeanDefinition parse(Element element, DefaultListableBeanFactory beanFactory) {
		BeanDefinition beanDefinition = new BeanDefinition();
		beanDefinition.setBeanClass(AnnotationAwareAspectJAutoProxyCreator.class);
		beanFactory.registerBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME, beanDefinition);
		return null;
	}
}
