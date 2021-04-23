package org.zk.simplespring.beans.factory;

import org.zk.simplespring.BeanFactory;

public interface BeanFactoryAware {

	void setBeanFactory(BeanFactory beanFactory);
}
