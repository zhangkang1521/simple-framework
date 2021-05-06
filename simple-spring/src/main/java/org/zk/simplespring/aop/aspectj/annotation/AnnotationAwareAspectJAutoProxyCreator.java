package org.zk.simplespring.aop.aspectj.annotation;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.aop.Advisor;
import org.zk.simplespring.aop.framework.ProxyFactory;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.BeanFactoryAware;
import org.zk.simplespring.beans.factory.config.BeanPostProcessor;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AspectJ代理创建
 */
public class AnnotationAwareAspectJAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {

	public static final Logger log = LoggerFactory.getLogger(AnnotationAwareAspectJAutoProxyCreator.class);

	private DefaultListableBeanFactory beanFactory;


	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		List<Advisor> specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName);
		log.info("找到{}的增强{}", beanName, specificInterceptors);
		if (specificInterceptors.size() > 0) {
			log.info("创建{}的代理", beanName);
			return createProxy(bean, specificInterceptors);
		}
		return bean;
	}

	/**
	 * 将@Aspect注解的类的@Before @After @Around 注解的方法转换成Advisor
	 * @param aClass
	 * @param beanName
	 * @return
	 */
	private List<Advisor> getAdvicesAndAdvisorsForBean(Class<?> aClass, String beanName) {
		// 本身是拦截器，无需增强
		if (isAspect(aClass)) {
			return Collections.emptyList();
		}
		List<Advisor> advisors = new ArrayList<>();
		beanFactory.getBeanDefinitionMap().forEach((beanName2, beanDefinition) -> {
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

	private Object createProxy(Object bean, List<Advisor> specificInterceptors) {
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(bean);
		proxyFactory.setAdvisors(specificInterceptors);
		return proxyFactory.getProxy();
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = (DefaultListableBeanFactory)beanFactory;
	}
}
