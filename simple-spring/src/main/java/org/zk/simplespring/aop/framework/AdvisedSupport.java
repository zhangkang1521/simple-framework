package org.zk.simplespring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.zk.simplespring.aop.Advisor;
import org.zk.simplespring.aop.MethodMatcher;
import org.zk.simplespring.aop.Pointcut;
import org.zk.simplespring.aop.PointcutAdvisor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AdvisedSupport {

    /**
     * 被代理目标对象
     */
    private Object target;

    /**
     * Advisor
     */
    private List<Advisor> advisors;

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public List<Advisor> getAdvisors() {
        return advisors;
    }

    public void setAdvisors(List<Advisor> advisors) {
        this.advisors = advisors;
    }

    public List<MethodInterceptor> advisorsToMethodInterceptors(Method method) {
        List<MethodInterceptor> methodInterceptors = new ArrayList<>(advisors.size());
        for (Advisor advisor : advisors) {
            if (advisor.getAdvice() instanceof MethodInterceptor) {
                // 符合切点表达式的才需要加入拦截器链
                if (match(method, advisor)) {
                    methodInterceptors.add((MethodInterceptor) advisor.getAdvice());
                }
            } else {
                throw new RuntimeException("unSupport Advise " + advisor.getAdvice().getClass());
            }
        }
        return methodInterceptors;
    }

    /**
     * 判断指定方法是否需要代理
     * @param method
     * @param advisor
     * @return
     */
    private boolean match(Method method, Advisor advisor) {
        if (advisor instanceof PointcutAdvisor) {
            // 接口方法
            Pointcut pointcut = ((PointcutAdvisor) advisor).getPointcut();
            MethodMatcher methodMatcher = pointcut.getMethodMatcher();
            if (methodMatcher.matches(method, target.getClass())) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
