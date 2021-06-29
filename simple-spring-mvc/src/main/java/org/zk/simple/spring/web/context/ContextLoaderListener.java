package org.zk.simple.spring.web.context;

import org.zk.simple.spring.web.context.support.AnnotationConfigWebApplicationContext;
import org.zk.simple.spring.web.context.support.XmlWebApplicationContext;
import org.zk.simplespring.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * spring-mvc启动Listener
 */
public class ContextLoaderListener implements ServletContextListener {

	private WebApplicationContext webApplicationContext;

	public static final String CONTEXT_CONFIG_LOCATION_PARAM = "contextConfigLocation";

	public ContextLoaderListener() {

	}

	public ContextLoaderListener(Class<?> annotationClass) {
		webApplicationContext = new AnnotationConfigWebApplicationContext(annotationClass);
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		if (webApplicationContext == null) {
			String contextConfigLocation = sce.getServletContext().getInitParameter(CONTEXT_CONFIG_LOCATION_PARAM);
			webApplicationContext = new XmlWebApplicationContext(contextConfigLocation);
		}
		webApplicationContext.setServletContext(sce.getServletContext());
		sce.getServletContext().setAttribute(WebApplicationContext.WEB_APPLICATION_CONTEXT, webApplicationContext);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO  关闭容器
	}
}
