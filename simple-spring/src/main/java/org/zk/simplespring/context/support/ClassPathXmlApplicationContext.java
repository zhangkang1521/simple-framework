package org.zk.simplespring.context.support;

/**
 * 只负责存储配置文件，
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

	// TODO spring不存在此处
	private String configLocation;

	public ClassPathXmlApplicationContext(String configLocation) {
		this.configLocation = configLocation;
		this.refresh();
	}

	@Override
	protected String getConfigLocation() {
		return configLocation;
	}


}
