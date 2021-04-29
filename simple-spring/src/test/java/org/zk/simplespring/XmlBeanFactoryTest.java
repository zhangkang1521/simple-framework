package org.zk.simplespring;

import org.junit.Test;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.xml.XmlBeanFactory;

public class XmlBeanFactoryTest {

	@Test
	public void testLoadBeanDefinition() {
		BeanFactory beanFactory = new XmlBeanFactory("applicationContext.xml");
		Object user = beanFactory.getBean("user");
		Object user2 = beanFactory.getBean("user");
	}
}
