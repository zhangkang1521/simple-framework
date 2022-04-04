package org.zk.simpledemo;

import org.junit.Test;
import org.zk.simpledemo.domain.User;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.xml.XmlBeanFactory;
import org.zk.simplespring.core.io.ClassPathResource;

/**
 * Unit test for simple App.
 */
public class SpringTest {

	@Test
	public void testSpring() {
		BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
		User user = (User)beanFactory.getBean("user");
	}

}
