package org.zk.domain;

import org.zk.simplespring.beans.factory.FactoryBean;

public class UserFactory implements FactoryBean<User> {
	@Override
	public User getObject() {
		return new User();
	}
}
