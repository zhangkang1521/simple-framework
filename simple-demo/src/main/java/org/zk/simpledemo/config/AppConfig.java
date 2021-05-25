package org.zk.simpledemo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.zk.simple.spring.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.zk.simple.spring.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
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
public class AppConfig implements BeanFactoryAware {

	private BeanFactory beanFactory;

	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		return new RequestMappingHandlerMapping();
	}

	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		return new RequestMappingHandlerAdapter();
	}

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/view/");
		internalResourceViewResolver.setSuffix(".jsp");
		return internalResourceViewResolver;
	}

//	@Bean
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/zk");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		return dataSource;
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// TODO 参数中注入dataSource
		sqlSessionFactoryBean.setDataSource(dataSource());
		sqlSessionFactoryBean.setMapperLocation("mappers");
		return sqlSessionFactoryBean;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
}
