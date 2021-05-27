package org.zk.simple.spring.web.servlet.config.annotation;

import org.zk.simple.spring.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.zk.simple.spring.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.zk.simplespring.context.annotation.Bean;
import org.zk.simplespring.context.annotation.Configuration;

/**
 * 注入{@link RequestMappingHandlerMapping}, {@link RequestMappingHandlerAdapter}
 */
@Configuration
public class DelegatingWebMvcConfiguration {

	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		return new RequestMappingHandlerMapping();
	}

	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		return new RequestMappingHandlerAdapter();
	}
}
