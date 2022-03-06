package org.zk.simplespring.beans.factory.support;

import org.zk.simplespring.core.io.DefaultResourceLoader;
import org.zk.simplespring.core.io.ResourceLoader;

/**
 * @author zhangkang
 * @create 2022/2/27 20:05
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

	/**
	 * Bean注册接口，实现类为DefaultListableBeanFactory
	 */
	private BeanDefinitionRegistry registry;

	private ResourceLoader resourceLoader;

	protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
		this(registry, new DefaultResourceLoader());
	}

	public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
		this.registry = registry;
		this.resourceLoader = resourceLoader;
	}

	@Override
	public BeanDefinitionRegistry getRegistry() {
		return registry;
	}

	@Override
	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
}
