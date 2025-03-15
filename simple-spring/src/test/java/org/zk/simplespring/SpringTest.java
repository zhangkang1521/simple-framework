package org.zk.simplespring;

import org.junit.Assert;
import org.junit.Test;
import org.zk.aop.Target;
import org.zk.aop.TargetImpl2;
import org.zk.aop.TargetInjectService;
import org.zk.config.AppConfig;
import org.zk.domain.Order;
import org.zk.domain.User;
import org.zk.domain.UserFactory;
import org.zk.service.UserService;
import org.zk.simplespring.context.annotation.AnnotationConfigApplicationContext;
import org.zk.simplespring.context.event.ContextClosedEvent;
import org.zk.simplespring.context.support.ApplicationContext;
import org.zk.simplespring.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;

public class SpringTest {

	@Test
	public void testGetBean() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//		applicationContext.publishEvent(new ContextRefreshedEvent(applicationContext));
//		applicationContext.publishEvent(new ContextClosedEvent(applicationContext));
		//		Assert.assertEquals(100, user.getId());
//		Assert.assertEquals("zk", user.getUsername());
		applicationContext.close();
//		applicationContext.registerShutdownHook();
	}

	@Test
	public void testBeanFactoryPostProcessor() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beanFactoryPostProcessor.xml");
		User user =  (User)applicationContext.getBean("user");
		Assert.assertEquals(100, user.getId());
		Assert.assertEquals("MyBeanFactoryPostProcessor add!!", user.getUsername());
	}

	@Test
	public void testBeanPostProcessor() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beanPostProcessor.xml");
		User user =  (User)applicationContext.getBean("user");
		Assert.assertEquals(100, user.getId());
		Assert.assertEquals("MyBeanPostProcessor modified!!", user.getUsername());
	}

	@Test
	public void testFactoryBean() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		User user = (User) applicationContext.getBean("user2");
		Assert.assertEquals(101, user.getId());
		UserFactory userFactory = (UserFactory) applicationContext.getBean("&user2");
		// TODO
		// Assert.assertEquals(user, userFactory.getObject());
	}

	@Test
	public void getBeanByType() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Order order =  applicationContext.getBean(Order.class);
		Assert.assertNotNull(order);
	}

	@Test
	public void testAopJdkDynamic() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-aop.xml");
		Target target = (Target)applicationContext.getBean("target");
		target.sayHello("zk");
		target.sayWorld("zk");
	}

	@Test
	public void testAopAutowired() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-aop-autowired.xml");
		TargetInjectService target = (TargetInjectService)applicationContext.getBean("targetInjectService");
		System.out.println(target);
	}

	@Test
	public void testAopAutowired2() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-aop-autowired-2.xml");
		TargetImpl2 target = (TargetImpl2)applicationContext.getBean("target");
		System.out.println(target);
	}

	@Test
	public void testAopCglib() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-aop-cglib.xml");
		TargetImpl2 target = (TargetImpl2)applicationContext.getBean("target");
		Method method = TargetImpl2.class.getDeclaredMethod("sayTest");
		method.setAccessible(true);
		method.invoke(target);
		target.sayHello("zk");
//		target.sayWorld("zk");
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
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		User user = (User) applicationContext.getBean("user");
	}

	@Test
	public void testPlaceHolder() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-placeholder.xml");
		User user =  (User)applicationContext.getBean("user");
		Assert.assertEquals("zk", user.getUsername());
	}

	@Test
	public void testAutowired() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-autowired.xml");
		User user =  (User)applicationContext.getBean("user");
		Assert.assertEquals("zk", user.getUsername());
		Assert.assertEquals(applicationContext.getBean("order"), user.getOrder());
	}

}
