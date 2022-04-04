package org.zk.aop;

import org.zk.simplespring.beans.factory.annotation.Autowired;

/**
 * 测试注入aop对象
 */
public class TargetInjectService {

    // jdk 接口注入没有问题，实现注入报错
    // cglib 实现注入没问题
    @Autowired
    private TargetImpl2 target;


}
