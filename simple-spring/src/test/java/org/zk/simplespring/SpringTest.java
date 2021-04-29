package org.zk.simplespring;

import org.junit.Test;
import org.zk.domain.User;
import org.zk.service.UserService;
import org.zk.simplespring.context.support.ApplicationContext;
import org.zk.simplespring.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

	@Test
	public void test1() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService userService = (UserService)applicationContext.getBean("userService");
		userService.sayHello();
//		System.out.println(user);
	}

}
