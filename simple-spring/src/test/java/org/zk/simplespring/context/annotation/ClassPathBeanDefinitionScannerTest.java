package org.zk.simplespring.context.annotation;

import org.junit.Test;
import org.zk.simplespring.DefaultListableBeanFactory;

import static org.junit.Assert.*;

public class ClassPathBeanDefinitionScannerTest {

	@Test
	public void scan() {
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner();
		scanner.setDefaultListableBeanFactory(new DefaultListableBeanFactory());
		scanner.scan("org.zk.dao");
	}
}