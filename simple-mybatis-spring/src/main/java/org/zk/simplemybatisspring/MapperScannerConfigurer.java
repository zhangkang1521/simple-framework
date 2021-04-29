package org.zk.simplemybatisspring;

import org.zk.simplespring.DefaultListableBeanFactory;
import org.zk.simplespring.beans.factory.config.BeanDefinitionRegistryPostProcessor;

/**
 * 自动注册mybatis的Dao层接口BeanDefinition
 */
public class MapperScannerConfigurer implements BeanDefinitionRegistryPostProcessor {

	private String basePackage;
	private String sqlSessionFactoryBeanName;

	@Override
	public void postProcessBeanDefinitionRegistry(DefaultListableBeanFactory beanFactory) {
		ClassPathMapperScanner scanner = new ClassPathMapperScanner(beanFactory);
		scanner.setSqlSessionFactoryBeanName(sqlSessionFactoryBeanName);
		scanner.scan(basePackage);
	}

	@Override
	public void postProcessBeanFactory(DefaultListableBeanFactory beanFactory) {

	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public void setSqlSessionFactoryBeanName(String sqlSessionFactoryBeanName) {
		this.sqlSessionFactoryBeanName = sqlSessionFactoryBeanName;
	}
}
