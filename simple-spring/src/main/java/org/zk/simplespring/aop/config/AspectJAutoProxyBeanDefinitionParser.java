package org.zk.simplespring.aop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.beans.factory.xml.BeanDefinitionParser;

/**
 * <aop:aspectj-autoproxy/>标签解析，向容器中注入代理创建器AnnotationAwareAspectJAutoProxyCreator
 */
public class AspectJAutoProxyBeanDefinitionParser implements BeanDefinitionParser {

	public static final Logger log = LoggerFactory.getLogger(AspectJAutoProxyBeanDefinitionParser.class);

	@Override
	public BeanDefinition parse(Element element, DefaultListableBeanFactory beanFactory) {
		AopConfigUtils.registerAutoProxyCreatorIfNecessory(beanFactory);
		return null;
	}
}
