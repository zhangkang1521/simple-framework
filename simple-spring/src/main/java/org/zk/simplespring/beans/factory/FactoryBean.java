package org.zk.simplespring.beans.factory;

public interface FactoryBean<T> {

	T getObject();

	/**
	 * 返回需要创建的类
	 * <p>用于autowired类型判断</p>
	 * @return
	 */
	Class<?> getObjectType();

	// TODO 是否单例，现在默认是每次都调用getObject
}
