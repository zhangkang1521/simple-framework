package org.zk.simplespring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

	private AdvisedSupport advisedSupport;

	public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
		this.advisedSupport = advisedSupport;
	}

	// 创建代理
	@Override
	public Object getProxy() {
		return Proxy.newProxyInstance(this.getClass().getClassLoader(), advisedSupport.getTarget().getClass().getInterfaces(), this);
	}

	// 代理执行
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		List<MethodInterceptor> chain = advisedSupport.advisorsToMethodInterceptors(method);
		ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(advisedSupport.getTarget(), method, args, chain);
		return invocation.proceed();
	}


}
