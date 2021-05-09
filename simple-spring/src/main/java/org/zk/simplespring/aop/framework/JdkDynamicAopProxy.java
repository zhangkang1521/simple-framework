package org.zk.simplespring.aop.framework;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.zk.simplespring.aop.Advisor;
import org.zk.simplespring.aop.MethodMatcher;
import org.zk.simplespring.aop.Pointcut;
import org.zk.simplespring.aop.PointcutAdvisor;
import org.zk.simplespring.aop.aspectj.AspectJExpressionPointcut;
import org.zk.simplespring.aop.aspectj.annotation.InstantiationModelAwarePointcutAdvisorImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

	private Object target;
	private List<Advisor> advisors;

	public JdkDynamicAopProxy(Object target, List<Advisor> advisors) {
		this.target = target;
		this.advisors = advisors;
	}

	// 创建代理
	@Override
	public Object getProxy() {
		return Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}

	// 代理执行
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		List<MethodInterceptor> methodInterceptors = advisorsToMethodInterceptors(method, advisors);
		ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(target, method, args, methodInterceptors);
		return invocation.proceed();
	}

	private List<MethodInterceptor> advisorsToMethodInterceptors(Method method, List<Advisor> advisors) {
		List<MethodInterceptor> methodInterceptors = new ArrayList<>(advisors.size());
		for (Advisor advisor : advisors) {
			if (advisor.getAdvice() instanceof MethodInterceptor) {
				if (match(method, advisor)) {
					methodInterceptors.add((MethodInterceptor) advisor.getAdvice());
				}
			} else {
				throw new RuntimeException("unSupport Advise " + advisor.getAdvice().getClass());
			}
		}
		return methodInterceptors;
	}

	/**
	 * 判断指定方法是否需要代理
	 * @param method
	 * @param advisor
	 * @return
	 */
	private boolean match(Method method, Advisor advisor) {
		if (advisor instanceof PointcutAdvisor) {
			// 接口方法
			Pointcut pointcut = ((PointcutAdvisor) advisor).getPointcut();
			MethodMatcher methodMatcher = pointcut.getMethodMatcher();
			if (methodMatcher.matches(method, target.getClass())) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
}
