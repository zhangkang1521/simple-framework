package org.zk.simplespring.context.annotation;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.BeanDefinition;
import org.zk.simplespring.DefaultListableBeanFactory;

import java.beans.Introspector;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 扫描指定包下类，并注册为BeanDefinition
 */
public class ClassPathBeanDefinitionScanner {

	public static final Logger log = LoggerFactory.getLogger(ClassPathBeanDefinitionScanner.class);

	private DefaultListableBeanFactory defaultListableBeanFactory;

	public List<BeanDefinition> scan(String basePackage) {
		log.info("开始扫描{}，自动注册BeanDefinition", basePackage);
		List<BeanDefinition> beanDefinitions = findBeanDefinitions(basePackage);
		for (BeanDefinition beanDefinition : beanDefinitions) {
			String beanName = generateBeanName((String)beanDefinition.getBeanClass());
			log.info("scan register beanDefinition [{}]", beanName);
			defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition);
		}
		return beanDefinitions;
	}

	private List<BeanDefinition> findBeanDefinitions(String basePackage) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL resource = classLoader.getResource(basePackage.replace(".", "/"));
		if (resource == null) {
			throw new RuntimeException("not found package " + basePackage);
		}
		File rootDir = new File(resource.getFile());
		if (rootDir.isDirectory()) {
			List<BeanDefinition> beanDefinitions = new ArrayList<>();
			for (File file : rootDir.listFiles()) {
				BeanDefinition beanDefinition = new BeanDefinition();
				String baseName =  FilenameUtils.getBaseName(file.getName());
				beanDefinition.setBeanClass(basePackage + "." + baseName);
				beanDefinitions.add(beanDefinition);
			}
			return beanDefinitions;
		} else {
			throw new RuntimeException("base package [" + basePackage + "] must be a directory");
		}
	}

	/**
	 * 首字母小写
	 * @param className
	 * @return
	 */
	private String generateBeanName(String className) {
		int pos = className.lastIndexOf(".");
		return Introspector.decapitalize(className.substring(pos + 1));
	}

	public void setDefaultListableBeanFactory(DefaultListableBeanFactory defaultListableBeanFactory) {
		this.defaultListableBeanFactory = defaultListableBeanFactory;
	}
}
