package org.zk.simplespring.beans.factory.support;

import org.zk.simplespring.core.io.Resource;
import org.zk.simplespring.core.io.ResourceLoader;

/**
 * BeanDefinition读取接口
 *
 * @author zhangkang
 * @create 2022/2/27 19:48
 */
public interface BeanDefinitionReader {

	/**
	 * 获取BeanDefinition注册器
	 *
	 * @return
	 */
	BeanDefinitionRegistry getRegistry();

	/**
	 * 获取资源加载器
	 *
	 * @return
	 */
	ResourceLoader getResourceLoader();

	/**
	 * 加载BeanDefinition到容器中
	 *
	 * @param resource
	 */
	void loadBeanDefinitions(Resource resource);

	// void loadBeanDefinitions(Resource... resources) throws BeansException;

	/**
	 * 加载BeanDefinition到容器中
	 *
	 * @param location 会使用ResourceLoader转换为Resource
	 */
	void loadBeanDefinitions(String location);
}
