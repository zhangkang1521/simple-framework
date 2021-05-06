package org.zk.simplespring;

import org.junit.Test;
import org.zk.aop.Target;
import org.zk.domain.User;
import org.zk.domain.UserFactory;
import org.zk.service.UserService;
import org.zk.simplespring.context.support.ApplicationContext;
import org.zk.simplespring.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

	@Test
	public void test1() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserFactory userFactory = (UserFactory) applicationContext.getBean("&user");
		//		UserService userService = (UserService)applicationContext.getBean("userService");
//		userService.sayHello();
//		System.out.println(user);
	}

	@Test
	public void testFactoryBean() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		User user = (User) applicationContext.getBean("user");
		UserFactory userFactory = (UserFactory) applicationContext.getBean("&user");
		System.out.println(userFactory.getObjectType());
	}

	@Test
	public void getBeanByType() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		User user = (User) applicationContext.getBean(User.class);
		System.out.println(user);
	}

	@Test
	public void testAop() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Target target = (Target)applicationContext.getBean("target");
		target.sayHello("zk");
	}

}
