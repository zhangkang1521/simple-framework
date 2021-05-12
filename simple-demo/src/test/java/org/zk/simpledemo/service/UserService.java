package org.zk.simpledemo.service;

import org.zk.simpledemo.domain.User;
import org.zk.simplespring.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

	@Transactional
	List<User> findAll();
}
