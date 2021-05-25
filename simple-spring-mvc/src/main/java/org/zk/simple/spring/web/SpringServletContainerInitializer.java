package org.zk.simple.spring.web;

import org.zk.simplespring.beans.SpringBeanUtils;
import org.zk.simplespring.util.ClassUtils;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * 实现Servlet的ServletContainerInitializer接口，代替web.xml配置
 */
@HandlesTypes(WebApplicationInitializer.class)
public class SpringServletContainerInitializer implements ServletContainerInitializer {

	@Override
	public void onStartup(Set<Class<?>> c, ServletContext sc) throws ServletException {
		for (Class<?> clz : c) {
			if (!Modifier.isAbstract(clz.getModifiers())) {
				WebApplicationInitializer initializer = (WebApplicationInitializer)SpringBeanUtils.instantiateClass(clz);
				initializer.onStartup(sc);
			}
		}
	}
}
