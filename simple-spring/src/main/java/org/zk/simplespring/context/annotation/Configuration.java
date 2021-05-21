package org.zk.simplespring.context.annotation;

import org.zk.simplespring.stereotype.Component;

import java.lang.annotation.*;

/**
 * 标记配置类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component // Configuration会被当成一个bean
public @interface Configuration {
}
