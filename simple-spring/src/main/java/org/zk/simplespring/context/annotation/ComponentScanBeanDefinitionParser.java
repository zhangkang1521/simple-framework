package org.zk.simplespring.context.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.support.BeanDefinitionRegistry;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.beans.factory.xml.BeanDefinitionParser;

/**
 * 扫描<context:component-scan base-package="xxx"/>指定的包，注册BeanDefinition
 */
public class ComponentScanBeanDefinitionParser implements BeanDefinitionParser {

	public static final Logger log = LoggerFactory.getLogger(ComponentScanBeanDefinitionParser.class);

	@Override
	public BeanDefinition parse(Element element, BeanDefinitionRegistry registry) {
		String basePackage = element.getAttribute("base-package");
		log.info("开始扫描basePackage {}", basePackage);
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, true);
		scanner.setDefaultListableBeanFactory(registry);
		scanner.scan(basePackage);
		return null;
	}
}
