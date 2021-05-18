package org.zk.simple.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * uri到Handler的映射
 */
public interface HandlerMapping {

	/**
	 * 根据请求获取处理器
	 * @param request
	 * @return
	 */
	Object getHandler(HttpServletRequest request);
}
