package org.zk.simplemybatisspring;

import org.zk.simplemybatis.SqlSessionFactory;
import org.zk.simplespring.BeanDefinition;
import org.zk.simplespring.PropertyValue;
import org.zk.simplespring.RuntimeBeanReference;
import org.zk.simplespring.TypedStringValue;
import org.zk.simplespring.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.List;

public class ClassPathMapperScanner extends ClassPathBeanDefinitionScanner {

	private String sqlSessionFactoryBeanName;

	// 覆盖父类方法，对BeanDefinition做进一步处理，满足mybatis需要
	public List<BeanDefinition> scan(String basePackage) {
		List<BeanDefinition> beanDefinitions = super.scan(basePackage);
		//  <bean id="userDao" class="org.zk.simplemybatisspring.MapperFactoryBean">
		//        <property name="sqlSessionFactory" ref="sqlSessionFactory"></property>
		//        <property name="mapperInterface" value="org.zk.simpledemo.dao.UserDao"></property>
		//  </bean>
		for (BeanDefinition beanDefinition : beanDefinitions) {
			beanDefinition.addProperty(new PropertyValue("mapperInterface", new TypedStringValue((String)beanDefinition.getBeanClass())));
			beanDefinition.addProperty(new PropertyValue("sqlSessionFactory", new RuntimeBeanReference(sqlSessionFactoryBeanName)));
			beanDefinition.setBeanClass(org.zk.simplemybatisspring.MapperFactoryBean.class);
		}
		return beanDefinitions;
	}

	public void setSqlSessionFactoryBeanName(String sqlSessionFactoryBeanName) {
		this.sqlSessionFactoryBeanName = sqlSessionFactoryBeanName;
	}
}
