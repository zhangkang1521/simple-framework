package org.zk.config;

import org.zk.domain.User;
import org.zk.simplespring.context.annotation.Bean;
import org.zk.simplespring.context.annotation.Configuration;
import org.zk.simplespring.context.annotation.Import;

@Configuration
//@ComponentScan("org.zk.service")
//@Import(FooConfig.class)
//@Import(DemoImportSelector.class)
//@Import(DemoImportBeanDefinitionRegistrar.class)
@EnableUser("user0")
public class AppConfig {

	@Bean
	public User user() {
		return new User();
	}
}
