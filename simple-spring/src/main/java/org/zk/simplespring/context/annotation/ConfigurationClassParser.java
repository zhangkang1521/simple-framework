package org.zk.simplespring.context.annotation;

import org.zk.simplespring.beans.SpringBeanUtils;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Configuration注解的类解析
 */
class ConfigurationClassParser {

	private DefaultListableBeanFactory defaultListableBeanFactory;
	private ComponentScanAnnotationParser componentScanAnnotationParser;

	public ConfigurationClassParser(DefaultListableBeanFactory defaultListableBeanFactory) {
		this.defaultListableBeanFactory = defaultListableBeanFactory;
		this.componentScanAnnotationParser = new ComponentScanAnnotationParser(defaultListableBeanFactory);
	}

	public void parse(Class<?> clz) {

		// @ComponentScan解析
		ComponentScan componentScan = clz.getAnnotation(ComponentScan.class);
		if (componentScan != null) {
			componentScanAnnotationParser.parse(componentScan);
		}

		// @Import解析
		Set<Import> imports = new HashSet<>();
		collectImport(clz, imports);
		if (imports.size() > 0) {
			for (Import _import : imports) {
				Class<?> importClz = _import.value();
				// @Import注解的3种情况
				if (ImportSelector.class.isAssignableFrom(importClz)) {
					ImportSelector importSelector = (ImportSelector) SpringBeanUtils.instantiateClass(importClz);
					for (String className : importSelector.selectImport()) {
						parse(ClassUtils.forName(className));
					}
				} else if (ImportBeanDefinitionRegistrar.class.isAssignableFrom(importClz)) {
					ImportBeanDefinitionRegistrar registrar = (ImportBeanDefinitionRegistrar) SpringBeanUtils.instantiateClass(importClz);
					registrar.registerBeanDefinitions(clz, defaultListableBeanFactory);
				} else {
					parse(importClz);
				}
			}
		}

		// 将配置类BeanDefinition注入
		BeanDefinition configurationBeanDefinition = new BeanDefinition();
		configurationBeanDefinition.setBeanClass(clz);
		String configurationBeanName = SpringBeanUtils.generateBeanName(clz.getName());
		defaultListableBeanFactory.registerBeanDefinition(configurationBeanName, configurationBeanDefinition);

		// @Bean解析
		Method[] methods = clz.getMethods();
		for (Method method : methods) {
			if (method.getAnnotation(Bean.class) != null) {
				BeanDefinition beanDefinition = new BeanDefinition();
				beanDefinition.setBeanClass(method.getReturnType());
				beanDefinition.setFactoryBeanName(configurationBeanName);
				beanDefinition.setFactoryMethod(method);
				defaultListableBeanFactory.registerBeanDefinition(method.getName(), beanDefinition);
			}
		}
	}

	/**
	 * 递归解析Import注解，存在@Enable类注解
	 * @param clz
	 * @param imports
	 */
	private void collectImport(Class<?> clz, Set<Import> imports) {
		if (clz.getName().startsWith("java")) {
			return;
		}
		Import _import = clz.getAnnotation(Import.class);
		if (_import != null) {
			imports.add(_import);
		}
		Annotation[] annotations = clz.getAnnotations();
		for (Annotation annotation : annotations) {
			Class<?> annotationType = annotation.annotationType();
			collectImport(annotationType, imports);
		}
	}

}
