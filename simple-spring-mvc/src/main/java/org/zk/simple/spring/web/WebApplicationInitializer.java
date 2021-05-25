package org.zk.simple.spring.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *  {@link SpringServletContainerInitializer} 感兴趣该接口
 * @see org.zk.simple.spring.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer
 */
public interface WebApplicationInitializer {

	void onStartup(ServletContext servletContext) throws ServletException;
}
