package org.zk.simplespring.context.annotation;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.beans.SpringBeanUtils;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.support.BeanDefinitionRegistry;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.core.type.filter.AnnotationTypeFilter;
import org.zk.simplespring.core.type.filter.TypeFilter;
import org.zk.simplespring.stereotype.Component;

import java.beans.Introspector;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 扫描指定包下类，并注册为BeanDefinition
 * <p>
 * 默认含有typeFilter，过滤类上含有注解
 *  {@link org.zk.simplespring.stereotype.Component @Component},
 *  {@link org.zk.simplespring.stereotype.Repository @Repository},
 *  {@link org.zk.simplespring.stereotype.Service @Service},
 *  {@link org.zk.simplespring.stereotype.Controller @Controller}
 *  </p>
 */
public class ClassPathBeanDefinitionScanner {

	public static final Logger log = LoggerFactory.getLogger(ClassPathBeanDefinitionScanner.class);

	private BeanDefinitionRegistry registry;

	private final List<TypeFilter> includeFilters = new LinkedList<>();

	public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultIncludeFilter) {
		this.registry = registry;
		if (useDefaultIncludeFilter) {
			registerDefaultIncludeFilters();
		}
	}

	private void registerDefaultIncludeFilters() {
		this.includeFilters.add(new AnnotationTypeFilter(Component.class));
	}

	public List<BeanDefinition> scan(String basePackage) {
		log.info("开始扫描{}，自动注册BeanDefinition", basePackage);
		List<BeanDefinition> beanDefinitions = findBeanDefinitions(basePackage);
		for (BeanDefinition beanDefinition : beanDefinitions) {
			String beanName = SpringBeanUtils.generateBeanName((String)beanDefinition.getBeanClass());
			registry.registerBeanDefinition(beanName, beanDefinition);
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
				if (isCandidateComponent(beanDefinition)) {
					beanDefinitions.add(beanDefinition);
				}
			}
			return beanDefinitions;
		} else {
			throw new RuntimeException("base package [" + basePackage + "] must be a directory");
		}
	}

	private boolean isCandidateComponent(BeanDefinition beanDefinition) {
		for (TypeFilter typeFilter : includeFilters) {
			if (!typeFilter.match(beanDefinition)) {
				return false;
			}
		}
		return true;
	}



	public void setDefaultListableBeanFactory(BeanDefinitionRegistry registry) {
		this.registry = registry;
	}
}
