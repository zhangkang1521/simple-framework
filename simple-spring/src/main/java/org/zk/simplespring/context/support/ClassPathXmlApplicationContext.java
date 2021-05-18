package org.zk.simplespring.context.support;

public class ClassPathXmlApplicationContext extends AbstractRefreshableApplicationContext {

	public ClassPathXmlApplicationContext(String configLocation) {
		super(configLocation);
	}
}
