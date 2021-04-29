package org.zk.simplespring.beans.factory;

public interface FactoryBean<T> {

	T getObject();
}
