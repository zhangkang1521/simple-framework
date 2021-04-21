package org.zk.simpledemo.dao;


import org.zk.simpledemo.domain.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();
}
