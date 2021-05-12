package org.zk.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.domain.User;

import java.util.List;

public class UserServiceImpl implements UserService {

	public static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public List<User> findUserList() {
		log.info("find userList");
		return null;
	}

}
