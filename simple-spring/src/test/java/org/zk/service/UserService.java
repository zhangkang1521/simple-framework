package org.zk.service;

import org.zk.simplespring.beans.factory.annotation.Autowired;
import org.zk.simplespring.stereotype.Service;

@Service
public class UserService {

	@Autowired
	UserComponent userComponent;

	public void sayHello() {
		System.out.println(userComponent);
	}
}
