package org.zk.simplespring.aop.aspectj.annotation;

import org.aopalliance.aop.Advice;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.zk.simplespring.aop.Advisor;
import org.zk.simplespring.aop.Pointcut;
import org.zk.simplespring.aop.PointcutAdvisor;
import org.zk.simplespring.aop.aspectj.AspectJAfterAdvice;
import org.zk.simplespring.aop.aspectj.AspectJBeforeAdvice;
import org.zk.simplespring.aop.aspectj.AspectJExpressionPointcut;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class InstantiationModelAwarePointcutAdvisorImpl implements PointcutAdvisor {

	private Annotation annotation;
	private String expression;
	private AspectJExpressionPointcut aspectJExpressionPointcut;
	private Advice instantiatedAdvice;


	public InstantiationModelAwarePointcutAdvisorImpl(Annotation annotation, Method method, Object aspectInstance) {
		this.annotation = annotation;
		if (annotation instanceof Before) {
			expression = ((Before) annotation).value();
			instantiatedAdvice = new AspectJBeforeAdvice(method, aspectInstance);
		} else if (annotation instanceof After) {
			expression = ((After) annotation).value();
			instantiatedAdvice = new AspectJAfterAdvice(method, aspectInstance);
		}
		aspectJExpressionPointcut = new AspectJExpressionPointcut();
		aspectJExpressionPointcut.setExpression(expression);
	}

	@Override
	public Advice getAdvice() {
		return instantiatedAdvice;
	}

	public Annotation getAnnotation() {
		return annotation;
	}


	@Override
	public String toString() {
		return "InstantiationModelAwarePointcutAdvisorImpl(" + instantiatedAdvice + ")";
	}

	@Override
	public Pointcut getPointcut() {
		return aspectJExpressionPointcut;
	}
}
