package org.zk.simple.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理器适配器
 * @see org.zk.simple.spring.web.servlet.mvc.SimpleControllerHandlerAdapter
 * @see org.zk.simple.spring.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
 */
public interface HandlerAdapter {

	boolean supports(Object handler);

	ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
