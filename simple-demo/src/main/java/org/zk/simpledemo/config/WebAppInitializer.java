package org.zk.simpledemo.config;

import org.zk.simple.spring.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	public Class<?> getAnnotationClass() {
		return AppConfig.class;
	}
}
