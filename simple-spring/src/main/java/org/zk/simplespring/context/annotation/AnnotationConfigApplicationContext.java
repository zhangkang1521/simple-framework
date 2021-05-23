package org.zk.simplespring.context.annotation;

import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.context.support.AbstractApplicationContext;

public class AnnotationConfigApplicationContext extends AbstractApplicationContext {

	private AnnotatedBeanDefinitionReader reader;

	public AnnotationConfigApplicationContext(Class<?> annotationClass) {
		this.beanFactory = new DefaultListableBeanFactory();
		this.reader = new AnnotatedBeanDefinitionReader(this.beanFactory);
		this.reader.register(annotationClass);
		refresh();
	}

	public void obtainFreshBeanFactory() {
		// 构造函数中已经创建了beanFactory，这里什么都不做
	}

}
