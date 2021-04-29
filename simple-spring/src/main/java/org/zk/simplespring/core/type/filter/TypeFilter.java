package org.zk.simplespring.core.type.filter;

import org.zk.simplespring.BeanDefinition;

public interface TypeFilter {

	boolean match(BeanDefinition beanDefinition);
}
