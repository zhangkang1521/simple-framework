package org.zk.simplespring.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class AspectJBeforeAdvice implements MethodInterceptor {

	public static final Logger log = LoggerFactory.getLogger(AspectJBeforeAdvice.class);

	/**
	 * aspectJ增强方法
	 */
	private Method aspectJMethod;

	private Object aspectInstance;

	public AspectJBeforeAdvice(Method aspectJMethod, Object aspectInstance) {
		this.aspectJMethod = aspectJMethod;
		this.aspectInstance = aspectInstance;
	}

	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		 this.aspectJMethod.invoke(aspectInstance, null);
		return mi.proceed();
	}
}
