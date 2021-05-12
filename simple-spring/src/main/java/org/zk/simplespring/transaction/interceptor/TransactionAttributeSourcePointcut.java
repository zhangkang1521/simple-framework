package org.zk.simplespring.transaction.interceptor;

import org.zk.simplespring.aop.MethodMatcher;
import org.zk.simplespring.aop.Pointcut;
import org.zk.simplespring.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 事务切点，判断是否有事务注解
 */
public class TransactionAttributeSourcePointcut implements Pointcut, MethodMatcher {

	@Override
	public MethodMatcher getMethodMatcher() {
		return this;
	}

	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		return method.getAnnotation(Transactional.class) != null;
	}
}
