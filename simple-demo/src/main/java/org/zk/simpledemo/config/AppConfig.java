package org.zk.simpledemo.config;

import org.zk.simple.spring.boot.autoconfigure.EnableAutoConfiguration;
import org.zk.simple.spring.web.servlet.view.InternalResourceViewResolver;
import org.zk.simplemybatisspring.SqlSessionFactoryBean;
import org.zk.simplemybatisspring.annotation.MapperScan;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.BeanFactoryAware;
import org.zk.simplespring.context.annotation.Bean;
import org.zk.simplespring.context.annotation.ComponentScan;
import org.zk.simplespring.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan({"org.zk.simpledemo.controller", "org.zk.simpledemo.service"})
@MapperScan(basePackage = "org.zk.simpledemo.dao", sqlSessionFactoryRef = "sqlSessionFactory")
@EnableAutoConfiguration
public class AppConfig implements BeanFactoryAware {

	private BeanFactory beanFactory;

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/view/");
		internalResourceViewResolver.setSuffix(".jsp");
		return internalResourceViewResolver;
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// 有循环依赖问题，暂时这样处理
		sqlSessionFactoryBean.setDataSource((DataSource) beanFactory.getBean("dataSource"));
		sqlSessionFactoryBean.setMapperLocation("mappers");
		return sqlSessionFactoryBean;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
}
