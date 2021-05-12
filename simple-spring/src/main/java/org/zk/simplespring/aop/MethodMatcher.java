package org.zk.simplespring.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {

	/**
	 * 判断切点是否匹配，代理创建和代理执行时用到
	 * @param method
	 * @param targetClass
	 * @return
	 */
	boolean matches(Method method, Class<?> targetClass);
}
