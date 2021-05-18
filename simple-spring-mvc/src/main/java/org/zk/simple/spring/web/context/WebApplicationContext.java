package org.zk.simple.spring.web.context;

import org.zk.simplespring.context.support.ApplicationContext;

import javax.servlet.ServletContext;


public interface WebApplicationContext extends ApplicationContext {

	/** 存放applicationContext到servletContext中的key */
	String WEB_APPLICATION_CONTEXT = "webApplicationContext";

	ServletContext getServletContext();
}
