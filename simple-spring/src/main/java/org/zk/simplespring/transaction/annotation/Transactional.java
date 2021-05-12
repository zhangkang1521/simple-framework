package org.zk.simplespring.transaction.annotation;

import java.lang.annotation.*;

/**
 * 事务注解，目前仅支持在接口方法上
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Transactional {
}
