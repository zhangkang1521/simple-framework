package org.zk.simpledemo.config;

import org.zk.simple.spring.boot.autoconfigure.EnableAutoConfiguration;
import org.zk.simple.spring.web.servlet.view.InternalResourceViewResolver;
import org.zk.simplemybatisspring.annotation.MapperScan;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.BeanFactoryAware;
import org.zk.simplespring.context.annotation.Bean;
import org.zk.simplespring.context.annotation.ComponentScan;
import org.zk.simplespring.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"org.zk.simpledemo.controller", "org.zk.simpledemo.service"})
//@MapperScan(basePackage = "org.zk.simpledemo.dao", sqlSessionFactoryRef = "sqlSessionFactory")
public class AppConfig  {

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("");
		internalResourceViewResolver.setSuffix("");
		return internalResourceViewResolver;
	}

}
