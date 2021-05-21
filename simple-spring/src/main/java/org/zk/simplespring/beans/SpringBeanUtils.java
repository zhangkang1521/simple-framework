package org.zk.simplespring.beans;

import java.beans.Introspector;

public class SpringBeanUtils {

	public static Object instantiateClass(Class<?> clz) {
		try {
			return clz.newInstance();
		} catch (IllegalAccessException | InstantiationException e) {
			throw new RuntimeException("实例化类异常" + clz, e);
		}
	}

	/**
	 * 根据类名生成beanName
	 * @param className
	 * @return
	 */
	public static String generateBeanName(String className) {
		int pos = className.lastIndexOf(".");
		return Introspector.decapitalize(className.substring(pos + 1));
	}
}
