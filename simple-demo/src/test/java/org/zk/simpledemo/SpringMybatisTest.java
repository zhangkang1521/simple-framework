package org.zk.simpledemo;

import org.junit.Test;
import org.zk.simpledemo.dao.UserDao;
import org.zk.simpledemo.domain.User;
import org.zk.simplespring.BeanFactory;
import org.zk.simplespring.XmlBeanFactory;

import java.util.List;

public class SpringMybatisTest {

	@Test
	public void test() {
		BeanFactory beanFactory = new XmlBeanFactory("spring-mybatis.xml");
		UserDao userDao = (UserDao)beanFactory.getBean("userDao");
		List userList = userDao.findAll();
		System.out.println(userList);
	}
}
