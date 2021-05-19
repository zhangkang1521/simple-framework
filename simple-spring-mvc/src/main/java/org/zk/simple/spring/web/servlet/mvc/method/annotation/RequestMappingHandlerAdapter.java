package org.zk.simple.spring.web.servlet.mvc.method.annotation;

import org.zk.simple.spring.web.method.HandlerMethod;
import org.zk.simple.spring.web.servlet.HandlerAdapter;
import org.zk.simple.spring.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 对Controller有@RequestMapping注解的方法进行适配执行
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {

	@Override
	public boolean supports(Object handler) {
		return handler instanceof HandlerMethod;
	}

	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Object controllerInstance = ((HandlerMethod) handler).getBean();
		Method method = ((HandlerMethod) handler).getMethod();
		return (ModelAndView) (method).invoke(controllerInstance, null);
	}
}
