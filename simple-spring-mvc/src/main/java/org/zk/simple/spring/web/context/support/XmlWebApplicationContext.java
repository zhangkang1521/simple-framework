package org.zk.simple.spring.web.context.support;

import org.zk.simple.spring.web.context.WebApplicationContext;
import org.zk.simplespring.context.support.AbstractApplicationContext;
import org.zk.simplespring.context.support.AbstractRefreshableConfigApplicationContext;

import javax.servlet.ServletContext;

public class XmlWebApplicationContext extends AbstractRefreshableConfigApplicationContext implements WebApplicationContext {

	private ServletContext servletContext;

	public XmlWebApplicationContext(String configLocation) {
		super(configLocation);
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
