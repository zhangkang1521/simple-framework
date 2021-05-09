package org.zk.simplespring.aop.aspectj.annotation;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.zk.simplespring.aop.Advisor;
import org.zk.simplespring.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;

import java.lang.reflect.Method;
import java.util.List;

/**
 * AspectJ代理创建
 */
public class AnnotationAwareAspectJAutoProxyCreator extends AbstractAdvisorAutoProxyCreator {


	public List<Advisor> findCandidateAdvisor() {
		// 父类查找实现Advisor的bean
		List<Advisor> advisors = super.findCandidateAdvisor();
		// 遍历@Aspect注解的类的方法，组装成Advisor
		this.beanFactory.getBeanDefinitionMap().forEach((beanName2, beanDefinition) -> {
			Class<?> clz = beanDefinition.resolveBeanClass();
			if (isAspect(clz)) {
				Object aspectInstance = beanFactory.getBean(beanName2);
				Method[] methods = clz.getDeclaredMethods();
				for (Method method : methods) {
					if (method.getAnnotation(Before.class) != null) {
						advisors.add(new InstantiationModelAwarePointcutAdvisorImpl(method.getAnnotation(Before.class), method, aspectInstance));
					} else if (method.getAnnotation(After.class) != null) {
						advisors.add(new InstantiationModelAwarePointcutAdvisorImpl(method.getAnnotation(After.class), method, aspectInstance));
					}
				}
			}
		});
		return advisors;
	}

	private boolean isAspect(Class<?> aClass) {
		if (aClass.getAnnotation(Aspect.class) != null) {
			return true;
		}
		return false;
	}

	public boolean isAdvisor(Class<?> aClass) {
		return isAspect(aClass) || super.isAdvisor(aClass);
	}

}
