package org.zk.simplespring.aop.aspectj;

import org.aspectj.weaver.tools.*;
import org.zk.simplespring.aop.MethodMatcher;
import org.zk.simplespring.aop.Pointcut;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class AspectJExpressionPointcut implements Pointcut, MethodMatcher {

	// 必须设值
	private String expression;

	// 需要自己构建
	private PointcutExpression pointcutExpression;

	private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>();

	static {
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.ARGS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.THIS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.TARGET);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.WITHIN);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);
	}

	public void buildPointcutExpression() {
		PointcutParser parser = initializePointcutParser();
		pointcutExpression = parser.parsePointcutExpression(expression,null, new PointcutParameter[0]);
	}

	private PointcutParser initializePointcutParser() {
		PointcutParser parser = PointcutParser
				.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
						SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
		// parser.registerPointcutDesignatorHandler(new BeanNamePointcutDesignatorHandler());
		return parser;
	}

	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		checkReadyToMatch();
		// method为接口中的方法；targetMethod为实现类中的方法
		Method targetMethod = method;
		ShadowMatch shadowMatch = getShadowMatch(targetMethod, method);
		if (shadowMatch.alwaysMatches()) {
			return true;
		}
		else if (shadowMatch.neverMatches()) {
			return false;
		}
		else {
			return false;
		}
	}

	private ShadowMatch getShadowMatch(Method targetMethod, Method originalMethod) {
		return this.pointcutExpression.matchesMethodExecution(targetMethod);
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public MethodMatcher getMethodMatcher() {
		checkReadyToMatch();
		return this;
	}

	private void checkReadyToMatch() {
		if (expression == null) {
			throw new IllegalStateException("Must set property 'expression' before attempting to match");
		}
		if(this.pointcutExpression == null) {
			buildPointcutExpression();
		}
	}
}