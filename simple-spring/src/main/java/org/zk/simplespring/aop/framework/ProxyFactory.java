package org.zk.simplespring.aop.framework;

public class ProxyFactory extends AdvisedSupport {


	public Object getProxy() {
		return createAopProxy().getProxy();
	}

	private AopProxy createAopProxy() {
		Class<?>[] interfaces = getTarget().getClass().getInterfaces();
		if (interfaces.length > 0) {
			return new JdkDynamicAopProxy(this);
		} else {
			return new CglibAopProxy(this);
		}
	}

}
