package org.zk.simple.spring.web.servlet.view;

import org.zk.simple.spring.web.servlet.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * jsp视图
 */
public class InternalResourceView implements View {

	public InternalResourceView(String path) {
		this.path = path;
	}

	private String path;

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		model.forEach((k, v) -> {
			request.setAttribute(k, v);
		});
		request.getRequestDispatcher(path).forward(request, response);
	}
}
