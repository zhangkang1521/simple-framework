package org.zk.simplespring.context.annotation;

import org.junit.Test;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

public class ClassPathBeanDefinitionScannerTest {

	@Test
	public void scan() {
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(new DefaultListableBeanFactory(), true);
		scanner.scan("org.zk.service");
		System.out.println(scanner);
	}
}