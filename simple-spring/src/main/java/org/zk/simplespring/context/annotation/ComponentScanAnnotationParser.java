package org.zk.simplespring.context.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

/**
 * 扫描@ComponentScan("xxx")指定的包，注册BeanDefinition
 */
public class ComponentScanAnnotationParser {

	public static final Logger log = LoggerFactory.getLogger(ComponentScanAnnotationParser.class);

	private DefaultListableBeanFactory defaultListableBeanFactory;

	public ComponentScanAnnotationParser(DefaultListableBeanFactory defaultListableBeanFactory) {
		this.defaultListableBeanFactory = defaultListableBeanFactory;
	}

	public BeanDefinition parse(ComponentScan componentScan) {
		String[] basePackages = componentScan.value();
		for (String basePackage : basePackages) {
			log.info("开始扫描basePackage {}", basePackage);
			ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(defaultListableBeanFactory, true);
			scanner.setDefaultListableBeanFactory(defaultListableBeanFactory);
			scanner.scan(basePackage);
		}
		return null;
	}
}
