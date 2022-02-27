package org.zk.domain;

import org.zk.simplespring.beans.factory.FactoryBean;

public class UserFactory implements FactoryBean<User> {
	@Override
	public User getObject() {
		User user = new User();
		user.setId(101);
		return user;
	}

	@Override
	public Class<?> getObjectType() {
		return User.class;
	}
}
