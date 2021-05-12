package org.zk.test.dao;

import org.zk.test.domain.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

}
