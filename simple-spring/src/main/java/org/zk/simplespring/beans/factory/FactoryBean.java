package org.zk.simplespring.beans.factory;

public interface FactoryBean<T> {

	/**
	 * 默认会缓存结果
	 * @return
	 */
	T getObject();

	/**
	 * 返回需要创建的类
	 * <p>用于autowired类型判断</p>
	 * @return
	 */
	Class<?> getObjectType();

}
