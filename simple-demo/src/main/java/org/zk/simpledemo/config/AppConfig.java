package org.zk.simpledemo.config;

import org.zk.simple.spring.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.zk.simple.spring.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.zk.simple.spring.web.servlet.view.InternalResourceViewResolver;
import org.zk.simplespring.context.annotation.Bean;
import org.zk.simplespring.context.annotation.ComponentScan;
import org.zk.simplespring.context.annotation.Configuration;

@Configuration
@ComponentScan("org.zk.simpledemo.controller")
public class AppConfig {

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
}
