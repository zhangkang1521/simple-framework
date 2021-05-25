package org.zk.simple.spring.web.context.support;

import org.zk.simple.spring.web.context.WebApplicationContext;
import org.zk.simplespring.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;

public class AnnotationConfigWebApplicationContext extends AnnotationConfigApplicationContext implements WebApplicationContext {

	private ServletContext servletContext;

	public AnnotationConfigWebApplicationContext(Class<?> annotationClass) {
		super(annotationClass);
	}


	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
