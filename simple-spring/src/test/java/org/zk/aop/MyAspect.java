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

    @Pointcut("execution(public * org.zk.aop.MyTarget.sayH*())")
//    @Pointcut("@annotation(org.zk.aop.DynamicDataSource)")
    public void pointCut1() {

    }

    @Before("pointCut1()")
    public void before() {
        log.info("=== before ===");
    }

    @After("pointCut1()")
    public void after() {
        log.info("=== after ===");
    }

//    @Around("pointCut1()")
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//        log.info("=== around start ===");
//        Object result = joinPoint.proceed();
//        log.info("=== around end ===");
//        return result;
//    }


}
