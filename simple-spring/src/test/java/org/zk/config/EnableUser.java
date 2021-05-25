package org.zk.config;

import org.zk.simplespring.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DemoImportBeanDefinitionRegistrar.class)
public @interface EnableUser {

	String value();
}
