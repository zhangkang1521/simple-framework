package org.zk.config;

import org.zk.domain.User;
import org.zk.simplespring.context.annotation.Bean;
import org.zk.simplespring.context.annotation.Configuration;

@Configuration
//@ComponentScan("org.zk.service")
//@Import(FooConfig.class)
//@Import(DemoImportSelector.class)
//@Import(DemoImportBeanDefinitionRegistrar.class)
public class ApplicationConfig {

	@Bean
	public User user() {
		return new User();
	}
}
