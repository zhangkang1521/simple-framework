package org.zk.simplespring.context.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.config.BeanDefinitionRegistryPostProcessor;
import org.zk.simplespring.beans.factory.config.BeanFactoryPostProcessor;
import org.zk.simplespring.beans.factory.config.BeanPostProcessor;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.context.ConfigurableApplicationContext;
import org.zk.simplespring.core.io.DefaultResourceLoader;

import java.util.List;

/**
 * 总体refresh方法
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

	private static final Logger log = LoggerFactory.getLogger(AbstractApplicationContext.class);


	@Override
	public void refresh() {
		// 1. 创建DefaultListableBeanFactory，loadBeanDefinition
		obtainFreshBeanFactory();

		DefaultListableBeanFactory beanFactory = getBeanFactory();

		// 2. 调用BeanFactory后置处理
		invokeBeanFactoryPostProcessors(beanFactory);

		// 3. 注册Bean后置处理器
		registerBeanPostProcessors(beanFactory);

		// 4.实例化所有单例bean
		finishBeanFactoryInitialization(beanFactory);
	}

	/**
	 * 创建beanFactory，loadBeanDefinition
	 */
	protected void obtainFreshBeanFactory() {
		refreshBeanFactory();
	}

	/**
	 * 交由子类AbstractRefreshableApplication实现
	 */
	protected abstract void refreshBeanFactory();

	/**
	 * 交由子类AbstractRefreshableApplication实现
	 * @return
	 */
	protected abstract DefaultListableBeanFactory getBeanFactory();

	/**
	 * 实例化并调用容器注册后处理器，容器后处理器
	 */
	private void invokeBeanFactoryPostProcessors(DefaultListableBeanFactory beanFactory) {
		List<String> beanNames = beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class);
		for (String beanName : beanNames) {
			BeanFactoryPostProcessor beanFactoryPostProcessor = (BeanFactoryPostProcessor) beanFactory.getBean(beanName);
			if (beanFactoryPostProcessor instanceof BeanDefinitionRegistryPostProcessor) {
				log.info("调用容器注册后处理器 {}", beanName);
				((BeanDefinitionRegistryPostProcessor) beanFactoryPostProcessor).postProcessBeanDefinitionRegistry(beanFactory);
			}
			log.info("调用容器后处理器 {}", beanName);
			beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
		}
	}

	/**
	 * 注册bean后置处理器，调用在getBean中
	 */
	private void registerBeanPostProcessors(DefaultListableBeanFactory beanFactory) {
		List<String> beanNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class);
		for (String beanName : beanNames) {
			BeanPostProcessor beanPostProcessor = (BeanPostProcessor) beanFactory.getBean(beanName);
			log.info("register BeanPostProcessor {}", beanName);
			beanFactory.addBeanPostProcessors(beanPostProcessor);
		}
	}

	/**
	 * 实例化单例bean
	 */
	private void finishBeanFactoryInitialization(DefaultListableBeanFactory beanFactory) {
		beanFactory.preInstantiateSingletons();
	}


	//

	@Override
	public Object getBean(String name) {
		return getBeanFactory().getBean(name);
	}

	@Override
	public <T> T getBean(Class<T> requiredType) {
		return getBeanFactory().getBean(requiredType);
	}

	@Override
	public <T> List<T> getBeanList(Class<T> requiredType) {
		return getBeanFactory().getBeanList(requiredType);
	}
}
