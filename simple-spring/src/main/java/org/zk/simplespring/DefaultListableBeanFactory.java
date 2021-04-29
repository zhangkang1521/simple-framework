package org.zk.simplespring;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.beans.factory.BeanFactoryAware;
import org.zk.simplespring.beans.factory.SpringBeanUtils;
import org.zk.simplespring.beans.factory.config.BeanPostProcessor;
import org.zk.simplespring.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory implements BeanFactory {

	private static final Logger log = LoggerFactory.getLogger(DefaultListableBeanFactory.class);

	/** 存储所有beanDefinition beanName -> BeanDefinition */
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

	/** 单例bean */
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

	/** bean后置处理器 */
	private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
		this.beanDefinitionMap.put(beanName, beanDefinition);
	}

	@Override
	public Object getBean(String beanName) {
		log.info("getBean {}", beanName);
		Object sharedInstance = getSingleton(beanName);
		if (sharedInstance != null) {
			log.info("返回缓存中的实例 {}", sharedInstance);
			sharedInstance = getObjectForBeanInstance(sharedInstance);
			return sharedInstance;
		}
		BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
		// 创建bean
		Object bean = createBeanInstance(beanName, beanDefinition);
		// 依赖注入
		populateBean(beanName, bean, beanDefinition);
		// 初始化
		initializeBean(beanName, bean, beanDefinition);
		addSingleton(beanName, bean);
		return getObjectForBeanInstance(bean);
	}




	private Object getObjectForBeanInstance(Object bean) {
		if (bean instanceof FactoryBean) {
			log.info("调用FactoryBean: {} getObject()返回实例", bean);
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
	private Object createBeanInstance(String beanName, BeanDefinition beanDefinition) {
		log.info("create bean instance {}", beanName);
		Class clz = beanDefinition.resolveBeanClass();
		return SpringBeanUtils.instantiateClass(clz);
	}

	/**
	 * 依赖注入
	 * @param bean
	 * @param beanDefinition
	 */
	private void populateBean(String beanName, Object bean, BeanDefinition beanDefinition) {
		log.info("populateBean {} start", beanName);
		List<PropertyValue> pvs = beanDefinition.getPropertyValueList();
		// @Autowired
		for (BeanPostProcessor bp : getBeanPostProcessors()) {
			if (bp instanceof InstantiationAwareBeanPostProcessor) {
				InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
				pvs = ibp.postProcessPropertyValues(pvs, bean, beanName);
				if (pvs == null) {
					return;
				}
			}
		}
		applyPropertyValues(beanName, bean, beanDefinition.getPropertyValueList());
		log.info("populateBean {} end", beanName);
	}

	/**
	 * 初始化bean
	 * @param name
	 * @param bean
	 * @param beanDefinition
	 */
	private void initializeBean(String name, Object bean, BeanDefinition beanDefinition) {
		log.info("初始化bean {}", name);
		invokeAwareMethod(name, bean);
		//TODO 前置处理 初始化方法 后置处理
	}

	private void invokeAwareMethod(String name, Object bean) {
		if (bean instanceof BeanFactoryAware) {
			((BeanFactoryAware) bean).setBeanFactory(this);
		}
	}

	private List<BeanPostProcessor> getBeanPostProcessors() {
		return beanPostProcessors;
	}

	public void addBeanPostProcessors(BeanPostProcessor beanPostProcessor) {
		this.beanPostProcessors.add(beanPostProcessor);
	}


	private void applyPropertyValues(String beanName, Object bean, List<PropertyValue> propertyValueList) {
		for (PropertyValue propertyValue : propertyValueList) {
			String propertyName = propertyValue.getName();
			Object sourceValue = propertyValue.getValue();
			Object resolvedValue = resolveValue(sourceValue);
			log.debug("[{}] set property [{}], value [{}]", beanName, propertyName, resolvedValue);
			try {
				BeanUtils.setProperty(bean, propertyName, resolvedValue);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException("bean set property exception", e);
			}
		}
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

	public <T> T getBean(Class<T> requiredType) {
		List<String> beanNames = getBeanNamesForType(requiredType);
		if (CollectionUtils.isEmpty(beanNames)) {
			throw new RuntimeException("没有找到bean，requiredType:" + requiredType);
		}
		if (beanNames.size() > 1) {
			throw new RuntimeException("找到多个bean错误，requiredType: " + requiredType);
		}
		return (T)getBean(beanNames.get(0));
	}

	public List<String> getBeanNamesForType(Class<?> type) {
		List<String> beanNames = new ArrayList<>();
		this.beanDefinitionMap.forEach((beanName, beanDefinition) -> {
			Class<?> clz = beanDefinition.resolveBeanClass();
			if(type.isAssignableFrom(clz)) {
				beanNames.add(beanName);
			}
		});
		return beanNames;
	}

	public Map<String, BeanDefinition> getBeanDefinitionMap() {
		return beanDefinitionMap;
	}

	/**
	 * 初始化bean
	 */
	public void preInstantiateSingletons() {
		log.info("初始化所有单例bean");
		for (String beanName : beanDefinitionMap.keySet()) {
			getBean(beanName);
		}
	}
}
