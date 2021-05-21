package org.zk.simplespring.context.annotation;

import java.lang.annotation.*;

/**
 * 注解配置引入
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Import {

	Class<?> value();
}
