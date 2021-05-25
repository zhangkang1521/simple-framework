package org.zk.simplemybatisspring.annotation;

import org.zk.simplespring.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MapperScannerRegistrar.class)
public @interface MapperScan {

	String basePackage();

	String sqlSessionFactoryRef();

}
