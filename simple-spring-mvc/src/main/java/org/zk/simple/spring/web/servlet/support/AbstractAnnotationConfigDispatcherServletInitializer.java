package org.zk.simple.spring.web.servlet.support;

import org.zk.simple.spring.web.WebApplicationInitializer;
import org.zk.simple.spring.web.context.ContextLoaderListener;
import org.zk.simple.spring.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * 注册{@link ContextLoaderListener} {@link DispatcherServlet}到web容器
 */
public abstract class AbstractAnnotationConfigDispatcherServletInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext sc) throws ServletException {
		// 注册Listener
		sc.addListener(new ContextLoaderListener(getAnnotationClass()));
		// 注册DispatcherServlet
		ServletRegistration.Dynamic servlet = sc.addServlet("dispatcherServlet", new DispatcherServlet());
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");

	}

	/**
	 * 启动配置类
	 * @return
	 */
	public abstract Class<?> getAnnotationClass();
}
