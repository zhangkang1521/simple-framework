package org.zk.simplespring.beans.factory.support;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.beans.PropertyValue;
import org.zk.simplespring.beans.factory.FactoryBean;
import org.zk.simplespring.beans.factory.InitializingBean;
import org.zk.simplespring.beans.factory.config.RuntimeBeanReference;
import org.zk.simplespring.beans.factory.config.TypedStringValue;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.BeanFactoryAware;
import org.zk.simplespring.beans.SpringBeanUtils;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.config.BeanPostProcessor;
import org.zk.simplespring.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory implements BeanFactory, BeanDefinitionRegistry {

	private static final Logger log = LoggerFactory.getLogger(DefaultListableBeanFactory.class);

	/** 存储所有beanDefinition beanName -> BeanDefinition */
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

	/** 单例bean */
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

	/** 缓存FactoryBean getObject方法返回的对象 */
	private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

	/** bean后置处理器 */
	private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
		log.info("注册BeanDefinition {}", beanName);
		this.beanDefinitionMap.put(beanName, beanDefinition);
	}

	@Override
	public boolean containsBeanDefinition(String beanName) {
		return beanDefinitionMap.containsKey(beanName);
	}

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
		BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
		if (beanDefinition == null) {
			throw new RuntimeException("未找到" + beanName + "的BeanDefinition，请检查配置");
		}
		// 创建bean
		Object bean = createBeanInstance(beanName, beanDefinition);
		// 依赖注入
		populateBean(beanName, bean, beanDefinition);
		// 初始化
		bean = initializeBean(beanName, bean, beanDefinition);
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
		String factoryBeanName = beanDefinition.getFactoryBeanName();
		Method factoryMethod = beanDefinition.getFactoryMethod();
		if (factoryBeanName != null && factoryMethod != null) {
			try {
//				Class<?>[] parameterTypes = factoryMethod.getParameterTypes();
//				Object[] resolvedDependency = new Object[parameterTypes.length];
//				for (int i = 0; i < parameterTypes.length; i++) {
//					resolvedDependency[i] = this.getBean(parameterTypes[i]);
//				}
				return factoryMethod.invoke(getBean(factoryBeanName));
			} catch (Exception e) {
				throw new RuntimeException("使用工厂方法创建bean实例异常", e);
			}
		} else {
			Class clz = beanDefinition.resolveBeanClass();
			return SpringBeanUtils.instantiateClass(clz);
		}
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
	private Object initializeBean(String name, Object bean, BeanDefinition beanDefinition) {
		log.info("初始化bean {}", name);
		invokeAwareMethod(name, bean);
		Object wrappedBean = bean;
		// 前置处理
		wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, name);
		// 初始化方法
		invokeInitMethod(wrappedBean, name);
		// 后置处理，可能返回bean的代理
		wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, name);
		return wrappedBean;
	}



	private Object applyBeanPostProcessorsBeforeInitialization(Object bean, String beanName) {
		Object result = bean;
		for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
			result = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
			if (result == null) {
				return result;
			}
		}
		return result;
	}

	private Object applyBeanPostProcessorsAfterInitialization(Object bean, String name) {
		Object result = bean;
		for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
			result = beanPostProcessor.postProcessAfterInitialization(result, name);
			if (result == null) {
				return result;
			}
		}
		return result;
	}

	private void invokeInitMethod(Object bean, String name) {
		if (bean instanceof InitializingBean) {
			((InitializingBean) bean).afterPropertiesSet();
		}
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

	public <T> List<T> getBeanList(Class<T> requiredType) {
		List<String> beanNames = getBeanNamesForType(requiredType);
		if (CollectionUtils.isEmpty(beanNames)) {
			throw new RuntimeException("没有找到bean，requiredType:" + requiredType);
		}
		List<T> beans = new ArrayList<>(beanNames.size());
		for (String beanName : beanNames) {
			beans.add((T)getBean(beanName));
		}
		return beans;
	}

	public List<String> getBeanNamesForType(Class<?> type) {
		List<String> beanNames = new ArrayList<>();
		this.beanDefinitionMap.forEach((beanName, beanDefinition) -> {
			Class<?> clz = beanDefinition.resolveBeanClass();
			if (FactoryBean.class.isAssignableFrom(clz)) {
				// 存在死循环 getBean("userDao") -> getBean("sqlSessionFactory") -> getBeanNamesForType(DataSource) -> getBean("&userDao") -> getBean("sqlSessionFactory")
				FactoryBean factoryBean = (FactoryBean) getBean(BeanFactory.FACTORY_BEAN_PREFIX + beanName);
				clz = factoryBean.getObjectType();
			}
			if (type.isAssignableFrom(clz)) {
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
