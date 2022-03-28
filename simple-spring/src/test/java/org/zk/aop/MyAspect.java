package org.zk.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
        log.info("=== Target.sayHello before ===");
    }

    @After("execution(String org.zk.aop.Target.say*(String))")
    public void after() {
        log.info("=== Target.say* after ===");
    }

    @Before("execution(String org.zk.aop.TargetImpl2.sayHello(String))")
    public void before2() {
        log.info("=== before sayHello ===");
    }

    @After("execution(String org.zk.aop.TargetImpl2.say*(String))")
    public void after2() {
        log.info("=== after say* ===");
    }



}
