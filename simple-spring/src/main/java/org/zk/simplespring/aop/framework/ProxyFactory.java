package org.zk.simplespring.aop.framework;

import org.zk.simplespring.aop.Advisor;

import java.util.List;

public class ProxyFactory {

	private Object target;
	private List<Advisor> advisors;


	public Object getProxy() {
		return createAopProxy().getProxy();
	}

	private AopProxy createAopProxy() {
		// TODO 判断用cglib 还是jdk动态代理
		return new JdkDynamicAopProxy(target, advisors);
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public void setAdvisors(List<Advisor> advisors) {
		this.advisors = advisors;
	}
}
