package org.zk.simplespring.aop.aspectj.annotation;

import org.aopalliance.aop.Advice;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.zk.simplespring.aop.Advisor;
import org.zk.simplespring.aop.aspectj.AspectJAfterAdvice;
import org.zk.simplespring.aop.aspectj.AspectJBeforeAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class InstantiationModelAwarePointcutAdvisorImpl implements Advisor {

	private Advice instantiatedAdvice;

	public InstantiationModelAwarePointcutAdvisorImpl(Annotation annotation, Method method, Object aspectInstance) {
		if (annotation instanceof Before) {
			instantiatedAdvice = new AspectJBeforeAdvice(method, aspectInstance);
		} else if (annotation instanceof After) {
			instantiatedAdvice = new AspectJAfterAdvice(method, aspectInstance);
		}
	}

	@Override
	public Advice getAdvice() {
		return instantiatedAdvice;
	}

	@Override
	public String toString() {
		return "InstantiationModelAwarePointcutAdvisorImpl(" + instantiatedAdvice + ")";
	}
}
