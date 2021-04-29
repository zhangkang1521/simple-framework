package org.zk.simplespring.util;

public class ClassUtils {

	public static Class<?> forName(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("没有找到类" + className, e);
		}
	}
}
