package org.zk.simple.spring.boot.autoconfigure;

import org.zk.simplespring.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启自动配置
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {

}
