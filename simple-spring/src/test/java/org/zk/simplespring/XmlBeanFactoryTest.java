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
		// 读取xml, 注册beanDefinition
		BeanFactory beanFactory = new XmlBeanFactory(resource);
		// 实例化（创建bean）
		// 依赖注入
		// aware
	    // 初始化 （前置处理，初始化（afterPropertySet, init-method），后置处理）
		User user = (User)beanFactory.getBean("user");
		Assert.assertEquals(100, user.getId());
		Assert.assertEquals("zk", user.getUsername());
	}

	@Test
	public void factoryBean() {
		Resource resource = new ClassPathResource("factory-bean.xml");
		BeanFactory beanFactory = new XmlBeanFactory(resource);
		User user = (User)beanFactory.getBean("user");
	}


}
