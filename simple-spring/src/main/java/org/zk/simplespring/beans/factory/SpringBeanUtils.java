package org.zk.simplespring.beans.factory;

public class SpringBeanUtils {

	public static Object instantiateClass(Class<?> clz) {
		try {
			return clz.newInstance();
		} catch (IllegalAccessException | InstantiationException e) {
			throw new RuntimeException("实例化类异常" + clz, e);
		}
	}
}
