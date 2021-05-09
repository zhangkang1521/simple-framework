package org.zk.simplespring.aop;

public interface PointcutAdvisor extends Advisor {

	Pointcut getPointcut();
}
