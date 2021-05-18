package org.zk.simple.spring.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 视图
 */
public interface View {

	/**
	 * 渲染
	 * @param model
	 * @param request
	 * @param response
	 */
	void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
