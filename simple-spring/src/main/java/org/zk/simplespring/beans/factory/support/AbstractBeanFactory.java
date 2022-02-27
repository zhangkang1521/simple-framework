package org.zk.simplespring.beans.factory.support;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.beans.PropertyValue;
import org.zk.simplespring.beans.SpringBeanUtils;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.BeanFactoryAware;
import org.zk.simplespring.beans.factory.FactoryBean;
import org.zk.simplespring.beans.factory.InitializingBean;
import org.zk.simplespring.beans.factory.config.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象bean工厂，模板方法模式
 * @author zhangkang
 * @create 2022/2/27 20:38
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

	private Logger log = LoggerFactory.getLogger(AbstractBeanFactory.class);

	/** 缓存FactoryBean getObject方法返回的对象 */
	private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

	/** bean后置处理器 */
	private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();



	@Override
	public Object getBean(String name) {
		log.info("getBean {}", name);
		String beanName = transformBeanName(name);
		Object sharedInstance = getSingleton(beanName);
		if (sharedInstance != null) {
			log.info("返回缓存中的实例 {}", sharedInstance);
			sharedInstance = getObjectForBeanInstance(name, beanName, sharedInstance);
			return sharedInstance;
		}
		BeanDefinition beanDefinition = getBeanDefinition(beanName);
		if (beanDefinition == null) {
			throw new RuntimeException("未找到" + beanName + "的BeanDefinition，请检查配置");
		}
		// 创建bean
		Object bean = createBean(beanName, beanDefinition);

		addSingleton(beanName, bean);
		return getObjectForBeanInstance(name, beanName, bean);
	}

	/**
	 * 如果带有&前缀则去除
	 * @param name
	 * @return
	 */
	private String transformBeanName(String name) {
		if (isFactoryBeanReference(name)) {
			return name.substring(1);
		}
		return name;
	}


	private Object getObjectForBeanInstance(String name, String beanName, Object bean) {
		if (bean instanceof FactoryBean) {
			if (isFactoryBeanReference(name)) {
				return bean;
			} else {
				if(factoryBeanObjectCache.containsKey(beanName)) {
					log.info("返回FactoryBean缓存中的bean", bean);
					return factoryBeanObjectCache.get(beanName);
				} else {
					log.info("调用FactoryBean: {} getObject()返回实例", bean);
					Object obj = ((FactoryBean) bean).getObject();
					factoryBeanObjectCache.put(beanName, obj);
					return obj;
				}
			}
		} else {
			return bean;
		}
	}

	private boolean isFactoryBeanReference(String beanName) {
		return beanName != null && beanName.startsWith(BeanFactory.FACTORY_BEAN_PREFIX);
	}


	public List<BeanPostProcessor> getBeanPostProcessors() {
		return beanPostProcessors;
	}

	public void addBeanPostProcessors(BeanPostProcessor beanPostProcessor) {
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
