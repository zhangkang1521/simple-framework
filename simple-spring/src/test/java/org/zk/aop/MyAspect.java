package org.zk.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 8/23/2018.
 */
@Aspect
public class MyAspect {

    public static final Logger log = LoggerFactory.getLogger(MyAspect.class);

//    @Pointcut("execution(public * org.zk.aop.MyTarget.sayH*())")
//    @Pointcut("@annotation(org.zk.aop.DynamicDataSource)")
    public void pointCut1() {

    }

    @Before("execution(String org.zk.aop.Target.sayHello(String))")
    public void before() {
        log.info("=== before ===");
    }

    @After("execution(String org.zk.aop.Target.xx(String))")
    public void after() {
        log.info("=== after ===");
    }



}
