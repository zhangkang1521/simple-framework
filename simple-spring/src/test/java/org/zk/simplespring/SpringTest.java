package org.zk.simplespring;

import org.junit.Test;
import org.zk.domain.User;
import org.zk.simplespring.context.support.ApplicationContext;
import org.zk.simplespring.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

	@Test
	public void test1() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		User user = (User)applicationContext.getBean("user");
		System.out.println(user);
	}
}
