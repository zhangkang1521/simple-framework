package org.zk.simplespring.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class AspectJAfterAdvice implements MethodInterceptor {

	public static final Logger log = LoggerFactory.getLogger(AspectJAfterAdvice.class);

	/**
	 * aspectJ增强方法
	 */
	private Method aspectJMethod;

	private Object aspectInstance;

	public AspectJAfterAdvice(Method aspectJMethod, Object aspectInstance) {
		this.aspectJMethod = aspectJMethod;
		this.aspectInstance = aspectInstance;
	}

	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		try {
			return mi.proceed();
		} finally {
			this.aspectJMethod.invoke(aspectInstance, null);
		}
	}
}
