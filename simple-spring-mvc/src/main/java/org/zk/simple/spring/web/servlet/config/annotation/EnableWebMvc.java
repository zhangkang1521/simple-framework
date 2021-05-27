package org.zk.simple.spring.web.servlet.config.annotation;

import org.zk.simplespring.context.annotation.Import;

import java.lang.annotation.*;

/**
 * spring-mvc注解化配置
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DelegatingWebMvcConfiguration.class)
public @interface EnableWebMvc {
}
