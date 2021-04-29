package org.zk.simpledemo;

import org.junit.Test;
import org.zk.simpledemo.dao.UserDao;
import org.zk.simplespring.context.support.ApplicationContext;
import org.zk.simplespring.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class SpringMybatisTest {

	@Test
	public void test() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		UserDao userDao = (UserDao)ctx.getBean("userDao");
		List userList = userDao.findAll();
		System.out.println(userList);
	}
}
