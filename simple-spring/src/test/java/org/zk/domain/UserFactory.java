package org.zk.domain;

import org.zk.simplespring.FactoryBean;

public class UserFactory implements FactoryBean<User> {
	@Override
	public User getObject() {
		return new User();
	}
}
