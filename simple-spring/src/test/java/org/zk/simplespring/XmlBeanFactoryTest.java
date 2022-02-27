package org.zk.simplespring;

import org.junit.Assert;
import org.junit.Test;
import org.zk.domain.User;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.xml.XmlBeanFactory;
import org.zk.simplespring.core.io.ClassPathResource;
import org.zk.simplespring.core.io.Resource;

public class XmlBeanFactoryTest {

	@Test
	public void testLoadBeanDefinition() {
		Resource resource = new ClassPathResource("bean-factory-1.xml");
		BeanFactory beanFactory = new XmlBeanFactory(resource);
		User user = (User)beanFactory.getBean("user");
		Assert.assertEquals(100, user.getId());
		Assert.assertEquals("zk", user.getUsername());
	}


}
