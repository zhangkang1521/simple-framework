package org.zk.simple.spring.web.servlet;

/**
 * 视图解析
 */
public interface ViewResolver {

	/**
	 * 将视图名解析成视图
	 * @param viewName
	 * @return
	 */
	View resolveViewName(String viewName);
}
