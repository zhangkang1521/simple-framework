package org.zk.simple.spring.web.servlet.handler;

import org.zk.simple.spring.web.servlet.HandlerMapping;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.BeanFactoryAware;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 简单url到Handler的映射
 */
public class SimpleUrlHandlerMapping implements HandlerMapping, BeanFactoryAware {

	private BeanFactory beanFactory;

	private final Map<String, String> urlMap = new HashMap<>();

	// TODO spring配置支持map
	{
		urlMap.put("/test", "userController");
	}

	@Override
	public Object getHandler(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String beanName = urlMap.get(uri);
		if (beanName != null)
			return beanFactory.getBean(beanName);
		else
			return null;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
}
