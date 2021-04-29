package org.zk.simplespring.core.type.filter;

import org.zk.simplespring.beans.factory.config.BeanDefinition;

public interface TypeFilter {

	boolean match(BeanDefinition beanDefinition);
}
