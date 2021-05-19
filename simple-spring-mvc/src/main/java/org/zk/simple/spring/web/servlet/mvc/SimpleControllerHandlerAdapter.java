package org.zk.simple.spring.web.servlet.mvc;

import org.zk.simple.spring.web.servlet.HandlerAdapter;
import org.zk.simple.spring.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对实现Controller接口的Handler执行进行适配
 */
public class SimpleControllerHandlerAdapter implements HandlerAdapter {

	@Override
	public boolean supports(Object handler) {
		return handler instanceof Controller;
	}

	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return ((Controller)handler).handleRequest(request, response);
	}
}
