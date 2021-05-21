package org.zk.simplespring.context.annotation;

import java.lang.annotation.*;

/**
 * 包扫描
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ComponentScan {

	String value();
}
