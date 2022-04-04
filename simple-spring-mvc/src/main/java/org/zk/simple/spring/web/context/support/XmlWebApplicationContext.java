package org.zk.simple.spring.web.context.support;

import org.zk.simple.spring.web.context.WebApplicationContext;
import org.zk.simplespring.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

// TODO 改为继承 AbstractRefreshableConfigApplicationContext
public class XmlWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {

	private ServletContext servletContext;

	public XmlWebApplicationContext(String configLocation) {
		super(configLocation);
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
