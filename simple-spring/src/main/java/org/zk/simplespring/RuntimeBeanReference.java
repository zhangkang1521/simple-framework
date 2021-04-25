package org.zk.simplespring;

/**
 * ref属性
 */
public class RuntimeBeanReference {
	private String beanName;

	public RuntimeBeanReference(String beanName) {
		this.beanName = beanName;
	}

	public String getBeanName() {
		return beanName;
	}


}
