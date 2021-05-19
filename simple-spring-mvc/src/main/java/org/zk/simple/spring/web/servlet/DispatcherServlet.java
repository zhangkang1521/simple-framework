package org.zk.simple.spring.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simple.spring.web.context.WebApplicationContext;
import org.zk.simple.spring.web.method.HandlerMethod;
import org.zk.simple.spring.web.servlet.mvc.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 请求分发Servlet
 */
public class DispatcherServlet extends HttpServlet {

	public static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

	private List<HandlerMapping> handlerMappings;

	private List<ViewResolver> viewResolvers;


	@Override
	public void init() throws ServletException {
		// spring-mvc在收到容器刷新事件后初始化9大组件，Servlet初始化在Listener之后，写这里也行
		log.info("初始化DispatcherServlet");
		WebApplicationContext webApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.WEB_APPLICATION_CONTEXT);
		handlerMappings = webApplicationContext.getBeanList(HandlerMapping.class);
		viewResolvers = webApplicationContext.getBeanList(ViewResolver.class);
	}

	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object handler = getHandler(req);
		// TODO 使用handlerAdapter
		ModelAndView modelAndView = null;
		if (handler instanceof Controller) {
			modelAndView = ((Controller)handler).handleRequest(req, resp);
		} else if (handler instanceof HandlerMethod) {
			try {
				Object controllerInstance = ((HandlerMethod) handler).getBean();
				Method method = ((HandlerMethod) handler).getMethod();
				modelAndView = (ModelAndView) (method).invoke(controllerInstance, null);
			} catch (Exception e) {
				throw new RuntimeException("执行handler错误", e);
			}
		}

		View view = resolveView(modelAndView.getView());
		view.render(modelAndView.getModel(), req, resp);
	}



	private Object getHandler(HttpServletRequest req) {
		for (HandlerMapping handlerMapping : handlerMappings) {
			Object handler = handlerMapping.getHandler(req);
			if (handler != null) {
				return handler;
			}
		}
		return null;
	}

	private View resolveView(String viewName) {
		for (ViewResolver viewResolver : viewResolvers) {
			View view = viewResolver.resolveViewName(viewName);
			if (view != null) {
				return view;
			}
		}
		return null;
	}


}
