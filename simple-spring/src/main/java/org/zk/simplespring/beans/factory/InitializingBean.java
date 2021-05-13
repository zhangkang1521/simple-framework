package org.zk.simplespring.beans.factory;

/**
 * bean初始化调用
 */
public interface InitializingBean {

	void afterPropertiesSet();
}
