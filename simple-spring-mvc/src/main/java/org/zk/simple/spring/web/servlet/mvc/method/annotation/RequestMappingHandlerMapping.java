package org.zk.simple.spring.web.servlet.mvc.method.annotation;

import org.zk.simple.spring.web.bind.annotation.RequestMapping;
import org.zk.simple.spring.web.method.HandlerMethod;
import org.zk.simple.spring.web.servlet.HandlerMapping;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.BeanFactoryAware;
import org.zk.simplespring.beans.factory.InitializingBean;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @RequestMapping注解的Controller url到Method映射
 */
public class RequestMappingHandlerMapping implements HandlerMapping, InitializingBean, BeanFactoryAware {

	private DefaultListableBeanFactory beanFactory;

	/** url -> method */
	private Map<String, HandlerMethod> mappings = new HashMap<>();



	@Override
	public HandlerMethod getHandler(HttpServletRequest request) {
		String uri = request.getRequestURI();
		return mappings.get(uri);
	}

	@Override
	public void afterPropertiesSet() {
		this.beanFactory.getBeanDefinitionMap().forEach((beanName, beanDefinition) -> {
			Class<?> clz = beanDefinition.resolveBeanClass();
			if (clz.getAnnotation(Controller.class) != null || clz.getAnnotation(RequestMapping.class) != null) {
				Object controllerInstance = beanFactory.getBean(beanName);
				Method[] methods = clz.getDeclaredMethods();
				for (Method method : methods) {
					RequestMapping clzRequestMapping = clz.getAnnotation(RequestMapping.class);
					RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
					if (methodRequestMapping != null) {
						mappings.put(buildRequestMappingUrl(clzRequestMapping, methodRequestMapping), new HandlerMethod(controllerInstance, method));
					}
				}
			}
		});
	}

	private String buildRequestMappingUrl(RequestMapping clzRequestMapping, RequestMapping methodRequestMapping) {
		if (clzRequestMapping != null) {
			return clzRequestMapping.value() + methodRequestMapping.value();
		} else {
			return methodRequestMapping.value();
		}
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = (DefaultListableBeanFactory) beanFactory;
	}
}
