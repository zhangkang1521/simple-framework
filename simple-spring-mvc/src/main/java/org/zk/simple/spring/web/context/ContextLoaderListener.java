package org.zk.simple.spring.web.context;

import org.zk.simple.spring.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * spring-mvc启动Listener
 */
public class ContextLoaderListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		XmlWebApplicationContext xmlWebApplicationContext = new XmlWebApplicationContext("applicationContext.xml");
		xmlWebApplicationContext.setServletContext(sce.getServletContext());
		sce.getServletContext().setAttribute(WebApplicationContext.WEB_APPLICATION_CONTEXT, xmlWebApplicationContext);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO  关闭容器
	}
}
