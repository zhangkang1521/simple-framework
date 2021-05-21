package org.zk.simplespring.context.annotation;

import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

/**
 * @Configuration注解的类解析
 */
class ConfigurationClassParser {

	private DefaultListableBeanFactory defaultListableBeanFactory;
	private ComponentScanAnnotationParser componentScanAnnotationParser;

	public ConfigurationClassParser(DefaultListableBeanFactory defaultListableBeanFactory) {
		this.defaultListableBeanFactory = defaultListableBeanFactory;
		this.componentScanAnnotationParser = new ComponentScanAnnotationParser(defaultListableBeanFactory);
	}

	public void parse(Class<?> clz) {
		ComponentScan componentScan = clz.getAnnotation(ComponentScan.class);
		if (componentScan != null) {
			componentScanAnnotationParser.parse(componentScan);
		}
	}
}
