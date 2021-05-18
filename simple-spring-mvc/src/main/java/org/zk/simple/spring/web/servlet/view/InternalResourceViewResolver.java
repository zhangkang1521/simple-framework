package org.zk.simple.spring.web.servlet.view;

import org.zk.simple.spring.web.servlet.View;
import org.zk.simple.spring.web.servlet.ViewResolver;

/**
 * jsp视图解析器
 */
public class InternalResourceViewResolver implements ViewResolver {

	private String prefix;
	private String suffix;

	@Override
	public View resolveViewName(String viewName) {
		String path = buildPath(viewName);
		return new InternalResourceView(path);
	}

	private String buildPath(String viewName) {
		return prefix + viewName + suffix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
