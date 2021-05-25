package org.zk.simplemybatisspring.annotation;

import org.zk.simplemybatisspring.ClassPathMapperScanner;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.context.annotation.ImportBeanDefinitionRegistrar;

public class MapperScannerRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(Class<?> annotationClass, DefaultListableBeanFactory defaultListableBeanFactory) {
		MapperScan mapperScan = annotationClass.getAnnotation(MapperScan.class);
		ClassPathMapperScanner scanner = new ClassPathMapperScanner(defaultListableBeanFactory);
		scanner.setSqlSessionFactoryBeanName(mapperScan.sqlSessionFactoryRef());
		scanner.scan(mapperScan.basePackage());
	}
}
