package org.zk.simplespring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectiveMethodInvocation implements MethodInvocation {

	private Object target;
	private Method method;
	private Object[] arguments;


	private List<MethodInterceptor> methodInterceptors;
	private int current = -1;


	public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments, List<MethodInterceptor> methodInterceptors) {
		this.target = target;
		this.method = method;
		this.arguments = arguments;
		this.methodInterceptors = methodInterceptors;
	}

	@Override
	public Object proceed() throws Throwable {
		if (current >= methodInterceptors.size() - 1) {
			return method.invoke(target, arguments);
		}
		MethodInterceptor methodInterceptor = methodInterceptors.get(++current);
		return methodInterceptor.invoke(this);
	}

	@Override
	public Method getMethod() {
		return method;
	}

	@Override
	public Object[] getArguments() {
		return arguments;
	}

	@Override
	public Object getThis() {
		return target;
	}

	@Override
	public AccessibleObject getStaticPart() {
		return null;
	}
}
