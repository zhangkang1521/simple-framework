package org.zk.simplespring.beans.factory.support;

import org.zk.simplespring.beans.factory.config.BeanDefinition;

/**
 * BeanDefinition注册接口
 * @author zhangkang
 * @create 2022/2/27 19:49
 */
public interface BeanDefinitionRegistry {

	/**
	 * 向注册表中注册 BeanDefinition
	 *
	 * @param beanName
	 * @param beanDefinition
	 */
	void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

	/**
	 * 使用Bean名称查询BeanDefinition
	 *
	 * @param beanName
	 * @return
	 */
//	BeanDefinition getBeanDefinition(String beanName);

	/**
	 * 判断是否包含指定名称的BeanDefinition
	 * @param beanName
	 * @return
	 */
	boolean containsBeanDefinition(String beanName);

	/**
	 * Return the names of all beans defined in this registry.
	 *
	 * 返回注册表中所有的Bean名称
	 */
//	String[] getBeanDefinitionNames();
}
