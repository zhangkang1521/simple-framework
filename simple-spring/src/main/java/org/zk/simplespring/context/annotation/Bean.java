package org.zk.simplespring.context.annotation;

import java.lang.annotation.*;

/**
 * 注入Bean
 * @see Configuration
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
}
