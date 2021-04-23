package org.zk.simplespring.context.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.DefaultListableBeanFactory;
import org.zk.simplespring.XmlBeanDefinitionReader;
import org.zk.simplespring.beans.factory.config.BeanPostProcessor;

import java.util.List;

public class ClassPathXmlApplicationContext implements ApplicationContext {

	private static final Logger log = LoggerFactory.getLogger(ClassPathXmlApplicationContext.class);

	private String configLocation;
	private DefaultListableBeanFactory beanFactory;

	public ClassPathXmlApplicationContext(String configLocation) {
		this.configLocation = configLocation;
		refresh();
	}

	private void refresh() {
		log.info("refresh");
		obtainFreshBeanFactory();
		invokeBeanFactoryPostProcessors();
		registerBeanPostProcessors();
		finishBeanFactoryInitialization();
	}

	/**
	 * 创建beanFactory，loadBeanDefinition
	 */
	private void obtainFreshBeanFactory() {
		beanFactory = new DefaultListableBeanFactory();
		log.info("create beanFactory {}", beanFactory);
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinition(this.configLocation);
	}

	/**
	 * 调用容器后处理器
	 */
	private void invokeBeanFactoryPostProcessors() {
	}

	/**
	 * 注册bean后置处理器
	 */
	private void registerBeanPostProcessors() {
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
	private void finishBeanFactoryInitialization() {
	}

	@Override
	public Object getBean(String name) {
		return beanFactory.getBean(name);
	}

	@Override
	public <T> T getBean(Class<T> requiredType) {
		return beanFactory.getBean(requiredType);
	}
}
