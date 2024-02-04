package org.zk.simplespring.beans.factory.support;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.FactoryBean;
import org.zk.simplespring.beans.factory.ListableBeanFactory;
import org.zk.simplespring.beans.factory.config.BeanDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DefaultListableBeanFactory，实现了BeanDefinitionRegistry， ListableBeanFactory接口
 * @author zhangkang
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ListableBeanFactory {

	private static final Logger log = LoggerFactory.getLogger(DefaultListableBeanFactory.class);

	/** 存储所有beanDefinition beanName -> BeanDefinition */
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
		log.info("register bean definition beanName:{} beanDefinition:{}", beanName, beanDefinition);
		this.beanDefinitionMap.put(beanName, beanDefinition);
	}

	@Override
	public boolean containsBeanDefinition(String beanName) {
		return beanDefinitionMap.containsKey(beanName);
	}


	@Override
	public BeanDefinition getBeanDefinition(String beanName) {
		return beanDefinitionMap.get(beanName);
	}

	public Map<String, BeanDefinition> getBeanDefinitionMap() {
		return beanDefinitionMap;
	}

	/**
	 * 初始化bean
	 */
	public void preInstantiateSingletons() {
		log.info("初始化所有单例bean");
		for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
			if (isFactoryBean(entry.getValue())) {
				getBean(BeanFactory.FACTORY_BEAN_PREFIX + entry.getKey());
			} else {
				getBean(entry.getKey());
			}
		}
	}

	private boolean isFactoryBean(BeanDefinition beanDefinition) {
		Class<?> clz = beanDefinition.resolveBeanClass();
		return FactoryBean.class.isAssignableFrom(clz);
	}

	public List<String> getBeanNamesForType(Class<?> type) {
		List<String> beanNames = new ArrayList<>();
		this.beanDefinitionMap.forEach((beanName, beanDefinition) -> {
			Class<?> clz = beanDefinition.resolveBeanClass();
			if (FactoryBean.class.isAssignableFrom(clz)) {
				// 存在死循环 getBean("userDao") -> getBean("sqlSessionFactory") -> getBeanNamesForType(DataSource) -> getBean("&userDao") -> getBean("sqlSessionFactory")
				// spring 中 getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)
//				FactoryBean factoryBean = (FactoryBean) getBean(BeanFactory.FACTORY_BEAN_PREFIX + beanName);
//				clz = factoryBean.getObjectType();
			}
			if (type.isAssignableFrom(clz)) {
				beanNames.add(beanName);
			}
		});
		return beanNames;
	}

	/** ================================ 实现ListableBeanFactory ======================================== */

	@Override
	public <T> List<T> getBeanList(Class<T> requiredType) {
		List<String> beanNames = getBeanNamesForType(requiredType);
		if (CollectionUtils.isEmpty(beanNames)) {
			// throw new RuntimeException("没有找到bean，requiredType:" + requiredType);
			return Collections.emptyList();
		}
		List<T> beans = new ArrayList<>(beanNames.size());
		for (String beanName : beanNames) {
			beans.add((T)getBean(beanName));
		}
		return beans;
	}

	@Override
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
}
