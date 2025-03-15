package org.zk.simplespring.context.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.beans.factory.config.BeanDefinitionRegistryPostProcessor;
import org.zk.simplespring.beans.factory.config.BeanFactoryPostProcessor;
import org.zk.simplespring.beans.factory.config.BeanPostProcessor;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.context.ApplicationEvent;
import org.zk.simplespring.context.ApplicationListener;
import org.zk.simplespring.context.ConfigurableApplicationContext;
import org.zk.simplespring.context.event.ApplicationEventMulticaster;
import org.zk.simplespring.context.event.ContextClosedEvent;
import org.zk.simplespring.context.event.ContextRefreshedEvent;
import org.zk.simplespring.context.event.SimpleApplicationEventMulticaster;
import org.zk.simplespring.core.io.DefaultResourceLoader;

import java.util.List;

/**
 * 总体refresh方法
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

	private static final Logger log = LoggerFactory.getLogger(AbstractApplicationContext.class);

	/**
	 * 事件广播器bean名称
	 */
	public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

	/**
	 * 事件广播器
	 */
	private ApplicationEventMulticaster applicationEventMulticaster;

	@Override
	public void refresh() {
		// 1. 创建DefaultListableBeanFactory，loadBeanDefinition（xml，注解方式不在这里加载beanDefinition）
		obtainFreshBeanFactory();

		DefaultListableBeanFactory beanFactory = getBeanFactory();

		prepareBeanFactory(beanFactory);

		// 2. 调用容器注册后处理器，容器后处理器
		invokeBeanFactoryPostProcessors(beanFactory);

		// 3. 注册Bean后置处理器
		registerBeanPostProcessors(beanFactory);

		// 初始化事件广播器，publishEvent会获取事件广播器，广播事件到所有Listener
		initApplicationEventMulticaster(beanFactory);

		// 注册事件监听器
		registerListeners(beanFactory);


		// 4.实例化所有单例bean
		finishBeanFactoryInitialization(beanFactory);

		// 发布容器刷新完成事件
		finishRefresh();
	}

	private void initApplicationEventMulticaster(DefaultListableBeanFactory beanFactory) {
		applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
		// 不用放入容器中，spring中是为了父子容器共用一个事件广播器
		// beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
	}

	/**
	 * 将事件监听器注册到事件广播器中
	 * @param beanFactory
	 */
	private void registerListeners(DefaultListableBeanFactory beanFactory) {
		List<ApplicationListener> applicationListeners = beanFactory.getBeanList(ApplicationListener.class);
		for (ApplicationListener listener : applicationListeners) {
			applicationEventMulticaster.addApplicationListener(listener);
		}
	}

	private void prepareBeanFactory(DefaultListableBeanFactory beanFactory) {
		// 还有其他spel表达式等
		beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
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
				// @Configuration 相关配置处理
				// Mybatis 自动注入MapperBean
				((BeanDefinitionRegistryPostProcessor) beanFactoryPostProcessor).postProcessBeanDefinitionRegistry(beanFactory);
			}
			log.info("调用容器后处理器 {}", beanName);
			// PropertyPlaceholderConfigurer bean占位符替换
			beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
		}
	}

	/**
	 * 注册bean后置处理器，调用在getBean中
	 */
	private void registerBeanPostProcessors(DefaultListableBeanFactory beanFactory) {
		// 注册bean后置处理器，代理用
		List<String> beanNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class);
		for (String beanName : beanNames) {
			BeanPostProcessor beanPostProcessor = (BeanPostProcessor) beanFactory.getBean(beanName);
			log.info("register BeanPostProcessor {}", beanName);
			beanFactory.addBeanPostProcessor(beanPostProcessor);
		}
	}

	/**
	 * 实例化单例bean
	 */
	private void finishBeanFactoryInitialization(DefaultListableBeanFactory beanFactory) {
		beanFactory.preInstantiateSingletons();
	}

	public void finishRefresh() {
		publishEvent(new ContextRefreshedEvent(this));
	}

	@Override
	public void publishEvent(ApplicationEvent event) {
		applicationEventMulticaster.multicastEvent(event);
	}


	// ========================== BeanFactory的方法 ===============================

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

	// =========================================================

	@Override
	public void close() {
		getBeanFactory().destroySingletons();
		publishEvent(new ContextClosedEvent(this));
	}


	@Override
	public void registerShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(this::close));
	}
}
