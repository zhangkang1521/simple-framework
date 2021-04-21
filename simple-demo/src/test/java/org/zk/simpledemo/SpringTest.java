package org.zk.simpledemo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.zk.simpledemo.domain.User;
import org.zk.simplespring.BeanFactory;
import org.zk.simplespring.XmlBeanFactory;

/**
 * Unit test for simple App.
 */
public class SpringTest {

	@Test
	public void testSpring() {
		BeanFactory beanFactory = new XmlBeanFactory("applicationContext.xml");
		User user = (User)beanFactory.getBean("user");
	}

}
