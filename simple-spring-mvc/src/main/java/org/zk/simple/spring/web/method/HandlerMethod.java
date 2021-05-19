package org.zk.simple.spring.web.method;

import org.zk.simple.spring.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * 封装bean和对应的方法
 * {@link RequestMappingHandlerMapping}
 */
public class HandlerMethod {

	private Object bean; // controller

	private Method method;

	public HandlerMethod(Object bean, Method method) {
		this.bean = bean;
		this.method = method;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
}
