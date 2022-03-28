package org.zk.simplespring.beans.factory.support;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.beans.PropertyValue;
import org.zk.simplespring.beans.SpringBeanUtils;
import org.zk.simplespring.beans.factory.*;
import org.zk.simplespring.beans.factory.config.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 创建bean,依赖注入，初始化，前置处理，后置处理
 * @author zhangkang
 * @create 2022/2/27 20:42
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

	private Logger log = LoggerFactory.getLogger(AbstractAutowireCapableBeanFactory.class);

	/**
	 * 创建bean的实例
	 * @param beanDefinition
	 * @return
	 */
	@Override
	public Object createBean(String beanName, BeanDefinition beanDefinition) {
		log.info("create bean instance {}", beanName);
		Object bean;

		// 创建bean
		String factoryBeanName = beanDefinition.getFactoryBeanName();
		Method factoryMethod = beanDefinition.getFactoryMethod();
		if (factoryBeanName != null && factoryMethod != null) {
			try {
//				Class<?>[] parameterTypes = factoryMethod.getParameterTypes();
//				Object[] resolvedDependency = new Object[parameterTypes.length];
//				for (int i = 0; i < parameterTypes.length; i++) {
//					resolvedDependency[i] = this.getBean(parameterTypes[i]);
//				}
				bean = factoryMethod.invoke(getBean(factoryBeanName));
			} catch (Exception e) {
				throw new RuntimeException("使用工厂方法创建bean实例异常", e);
			}
		} else {
			Class clz = beanDefinition.resolveBeanClass();
			bean = SpringBeanUtils.instantiateClass(clz);
		}

		// 依赖注入
		populateBean(beanName, bean, beanDefinition);
		// 初始化
		bean = initializeBean(beanName, bean, beanDefinition);

		// 注册实现了 DisposableBean 接口的 Bean 对象
		registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

		// 放入缓存
		addSingleton(beanName, bean);
		return bean;
	}

	private void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
		if (bean instanceof DisposableBean) {
			registerDisposableBean(beanName, (DisposableBean)bean);
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
		// @Autowired @Value
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
			throw new IllegalArgumentException("暂不支持的property");
		}
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
		if (bean instanceof Aware) {
			if (bean instanceof BeanFactoryAware) {
				((BeanFactoryAware) bean).setBeanFactory(this);
			}
			if (bean instanceof BeanNameAware) {
				((BeanNameAware) bean).setBeanName(name);
			}
		}
	}
}
