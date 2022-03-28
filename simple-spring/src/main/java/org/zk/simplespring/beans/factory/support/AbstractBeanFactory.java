package org.zk.simplespring.beans.factory.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.beans.factory.BeanFactoryUtils;
import org.zk.simplespring.beans.factory.FactoryBean;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.config.BeanPostProcessor;
import org.zk.simplespring.beans.factory.config.ConfigurableBeanFactory;
import org.zk.simplespring.util.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象bean工厂，模板方法模式
 * @author zhangkang
 * @create 2022/2/27 20:38
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

	private Logger log = LoggerFactory.getLogger(AbstractBeanFactory.class);



	/** bean后置处理器 */
	private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

	/**
	 * @Value 解析，占位符替换
	 */
	private List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();


    public void addEmbeddedValueResolver(StringValueResolver stringValueResolver) {
		this.embeddedValueResolvers.add(stringValueResolver);
	}

	public String resolveEmbeddedValue(String value) {
		String result = value;
		for (StringValueResolver resolver : this.embeddedValueResolvers) {
			result = resolver.resolveStringValue(result);
		}
		return result;
	}

	@Override
	public Object getBean(String name) {
		log.info("getBean {}", name);
		String beanName = BeanFactoryUtils.transformedBeanName(name);
		Object sharedInstance = getSingleton(beanName);
		if (sharedInstance != null) {
			// FactoryBean逻辑
			sharedInstance = getObjectForBeanInstance(name, beanName, sharedInstance);
			return sharedInstance;
		}
		BeanDefinition beanDefinition = getBeanDefinition(beanName);
		if (beanDefinition == null) {
			throw new RuntimeException("未找到" + beanName + "的BeanDefinition，请检查配置");
		}
		// 创建bean
		Object bean = createBean(beanName, beanDefinition);
		// FactoryBean逻辑
		return getObjectForBeanInstance(name, beanName, bean);
	}

	/**
	 * FactoryBean处理逻辑
	 * @param name
	 * @param beanName
	 * @param bean
	 * @return
	 */
	protected Object getObjectForBeanInstance(String name, String beanName, Object bean) {
		// 不是FactoryBean，无需处理
		if (!(bean instanceof FactoryBean)) {
			return bean;
		}
		// &xxx 获取的是FactoryBean的实例，也无需处理
		if (BeanFactoryUtils.isFactoryDereference(name)) {
			return bean;
		}

		// 从缓存中获取
		Object object = getCachedObjectForFactoryBean(beanName);

		// 缓存中没有就调用getObject方法获取
		if (object == null) {
			object = getObjectFromFactoryBean((FactoryBean<?>) bean, beanName);
		}
		return object;
	}




	public List<BeanPostProcessor> getBeanPostProcessors() {
		return beanPostProcessors;
	}

	@Override
	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
		this.beanPostProcessors.add(beanPostProcessor);
	}

	/**
	 * 由DefaultListableBeanFactory实现
	 * @param beanName
	 * @return
	 */
	protected abstract BeanDefinition getBeanDefinition(String beanName);

	/**
	 * 由AbstractAutowiredCapableBeanFactory实现
	 * @param beanName
	 * @param beanDefinition
	 * @return
	 */
	protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);






}
