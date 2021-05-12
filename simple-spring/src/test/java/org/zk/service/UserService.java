package org.zk.service;

import org.zk.domain.User;
import org.zk.simplespring.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

	@Transactional
	List<User> findUserList();
}
