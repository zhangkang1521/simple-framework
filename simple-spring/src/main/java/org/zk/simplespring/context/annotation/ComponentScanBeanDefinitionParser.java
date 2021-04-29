package org.zk.simplespring.context.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.zk.simplespring.BeanDefinition;
import org.zk.simplespring.DefaultListableBeanFactory;
import org.zk.simplespring.beans.factory.xml.BeanDefinitionParser;

import java.util.List;

public class ComponentScanBeanDefinitionParser implements BeanDefinitionParser {

	public static final Logger log = LoggerFactory.getLogger(ComponentScanBeanDefinitionParser.class);

	@Override
	public BeanDefinition parse(Element element, DefaultListableBeanFactory beanFactory) {
		String basePackage = element.getAttribute("base-package");
		log.info("开始扫描basePackage {}", basePackage);
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory, true);
		scanner.setDefaultListableBeanFactory(beanFactory);
		scanner.scan(basePackage);
		return null;
	}
}
