package org.zk.simplespring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;
import org.zk.aop.MyAspect;
import org.zk.aop.Target;
import org.zk.aop.TargetImpl;
import org.zk.simplespring.aop.aspectj.AspectJAfterAdvice;
import org.zk.simplespring.aop.aspectj.AspectJBeforeAdvice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ReflectiveMethodInvocationTest {

	@Test
	public void proceed() throws Throwable {
		Target target = new TargetImpl();
		Method method = target.getClass().getDeclaredMethod("sayHello", String.class);
		List<MethodInterceptor> methodInterceptors = new ArrayList<>();
		MyAspect myAspect = new MyAspect();
		methodInterceptors.add(new AspectJBeforeAdvice(MyAspect.class.getMethod("before"), myAspect));
		methodInterceptors.add(new AspectJAfterAdvice(MyAspect.class.getMethod("after"), myAspect));
		ReflectiveMethodInvocation reflectiveMethodInvocation = new ReflectiveMethodInvocation(target, method, new Object[]{"zk"}, methodInterceptors);
		Object result = reflectiveMethodInvocation.proceed();
		System.out.println(result);
	}
}