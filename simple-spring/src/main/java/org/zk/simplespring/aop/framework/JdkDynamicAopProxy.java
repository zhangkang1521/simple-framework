package org.zk.simplespring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.zk.simplespring.aop.Advisor;

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
		List<MethodInterceptor> methodInterceptors = advisorsToMethodInterceptors(advisors);
		ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(target, method, args, methodInterceptors);
		return invocation.proceed();
	}

	private List<MethodInterceptor> advisorsToMethodInterceptors(List<Advisor> advisors) {
		List<MethodInterceptor> methodInterceptors = new ArrayList<>(advisors.size());
		for (Advisor advisor : advisors) {
			if (advisor.getAdvice() instanceof MethodInterceptor) {
				methodInterceptors.add((MethodInterceptor) advisor.getAdvice());
			} else {
				throw new RuntimeException("unSupport Advise " + advisor.getAdvice().getClass());
			}
		}
		return methodInterceptors;
	}
}
