package org.zk.simplespring;

import org.junit.Test;
import org.zk.aop.Target;
import org.zk.config.ApplicationConfig;
import org.zk.domain.User;
import org.zk.domain.UserFactory;
import org.zk.service.UserService;
import org.zk.simplespring.context.annotation.AnnotationConfigApplicationContext;
import org.zk.simplespring.context.support.ApplicationContext;
import org.zk.simplespring.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

	@Test
	public void testGetBean() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		User user = (User) applicationContext.getBean("user");

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
//		target.sayWorld("zk");
		target.sayHello("zk");
	}

	@Test
	public void testTx() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService userService = (UserService) applicationContext.getBean("userService");
		userService.findUserList();
	}

	@Test
	public void testXmlAnnotation() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		User userService = (User) applicationContext.getBean("user");
	}

	@Test
	public void testAnnotation() {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		User user = (User) applicationContext.getBean("user");
	}

}
