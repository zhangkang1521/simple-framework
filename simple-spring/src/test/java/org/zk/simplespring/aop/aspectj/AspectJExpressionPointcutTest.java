package org.zk.simplespring.aop.aspectj;

import org.junit.Test;
import org.zk.aop.Target;
import org.zk.aop.TargetImpl;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class AspectJExpressionPointcutTest {

	@Test
	public void matches() throws Exception {
		String expression = "execution(String org.zk.aop.Target.sayHello(String))";
		AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
		aspectJExpressionPointcut.setExpression(expression);
		aspectJExpressionPointcut.buildPointcutExpression();
		Method method = Target.class.getMethod("sayHello", String.class);
		boolean match = aspectJExpressionPointcut.matches(method, TargetImpl.class);
		System.out.println(match);
	}
}