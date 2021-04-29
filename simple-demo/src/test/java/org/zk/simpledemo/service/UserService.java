package org.zk.simpledemo.service;

import org.zk.simpledemo.dao.UserDao;
import org.zk.simpledemo.domain.User;
import org.zk.simplespring.beans.factory.annotation.Autowired;
import org.zk.simplespring.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public List<User> findAll() {
		return userDao.findAll();
	}


}
