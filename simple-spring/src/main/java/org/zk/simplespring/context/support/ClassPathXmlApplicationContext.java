package org.zk.simplespring.context.support;

public class ClassPathXmlApplicationContext extends AbstractRefreshableConfigApplicationContext {

	public ClassPathXmlApplicationContext(String configLocation) {
		super(configLocation);
	}
}
