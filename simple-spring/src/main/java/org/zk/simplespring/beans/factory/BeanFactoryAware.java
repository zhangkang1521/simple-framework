package org.zk.simplespring.beans.factory;

public interface BeanFactoryAware extends Aware {

	void setBeanFactory(BeanFactory beanFactory);
}
