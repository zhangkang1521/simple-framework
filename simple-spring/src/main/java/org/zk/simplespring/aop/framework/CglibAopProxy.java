package org.zk.simplespring.aop.framework;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

public class CglibAopProxy implements AopProxy {

   private AdvisedSupport advisedSupport;


    public CglibAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advisedSupport.getTarget().getClass());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advisedSupport.getTarget()));
        return enhancer.create();
    }

    /**
     * 实现了cglib的拦截器接口
     */
    private class DynamicAdvisedInterceptor implements MethodInterceptor {

        private Object target;

        public DynamicAdvisedInterceptor(Object target) {
            this.target = target;
        }

        @Override
        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            // 所有方法进这里
            List<org.aopalliance.intercept.MethodInterceptor> chain = advisedSupport.advisorsToMethodInterceptors(method);
            ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(target, method, args, chain);
            return invocation.proceed();
        }
    }



}
