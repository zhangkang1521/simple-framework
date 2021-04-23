package org.zk.simplespring;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory implements BeanFactory {

	private static final Logger log = LoggerFactory.getLogger(DefaultListableBeanFactory.class);

	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
		this.beanDefinitionMap.put(beanName, beanDefinition);
	}

	@Override
	public Object getBean(String name) {
		log.info("获取bean:{}", name);
		Object sharedInstance = getSingleton(name);
		if (sharedInstance != null) {
			log.info("返回缓存中的实例:{}", sharedInstance);
			sharedInstance = getObjectForBeanInstance(sharedInstance);
			return sharedInstance;
		}
		BeanDefinition beanDefinition = beanDefinitionMap.get(name);
		try {
			Object bean = createBeanInstance(beanDefinition);
			populateBean(bean, beanDefinition);
			addSingleton(name, bean);
			bean = getObjectForBeanInstance(bean);
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Object getObjectForBeanInstance(Object bean) {
		if (bean instanceof FactoryBean) {
			log.info("调用FactoryBean.getObject()返回实例");
			return ((FactoryBean) bean).getObject();
		} else {
			return bean;
		}
	}

	/**
	 * 从缓存中获取
	 * @param name
	 * @return
	 */
	private Object getSingleton(String name) {
		return this.singletonObjects.get(name);
	}

	/**
	 * 加入缓存
	 * @param name
	 * @param bean
	 */
	private void addSingleton(String name, Object bean) {
		this.singletonObjects.put(name, bean);
	}

	/**
	 * 创建bean的实例
	 * @param beanDefinition
	 * @return
	 */
	private Object createBeanInstance(BeanDefinition beanDefinition) {
		try {
			Class clz = beanDefinition.resolveBeanClass();
			Object bean =  clz.newInstance();
			log.info("创建bean:{}", bean);
			return bean;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 依赖注入
	 * @param bean
	 * @param beanDefinition
	 */
	private void populateBean(Object bean, BeanDefinition beanDefinition) {
		log.info("populateBean {} start", bean);
		List<PropertyValue> propertyValueList = beanDefinition.getPropertyValueList();
		for (PropertyValue propertyValue : propertyValueList) {
			String propertyName = propertyValue.getName();
			Object sourceValue = propertyValue.getValue();
			Object resolvedValue = resolveValue(sourceValue);
			log.info("设置property:{}, value:{}", bean, propertyName, resolvedValue);
			try {
				BeanUtils.setProperty(bean, propertyName, resolvedValue);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		log.info("populateBean {} end", bean);
	}

	/**
	 * 解析property的sourceValue，sourceValue可能为TypedStringValue, RuntimeBeanReference等
	 * @param sourceValue
	 * @return
	 */
	private Object resolveValue(Object sourceValue) {
		if (sourceValue instanceof TypedStringValue) {
			return ((TypedStringValue) sourceValue).getValue();
		} else if (sourceValue instanceof RuntimeBeanReference) {
			log.info("获取依赖bean:{}", ((RuntimeBeanReference) sourceValue).getBeanName());
			return getBean(((RuntimeBeanReference) sourceValue).getBeanName());
		} else {
			throw new IllegalArgumentException("暂不支持的propery");
		}
	}
}
