package org.zk.simplespring.aop.framework.autoproxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.aop.Advisor;
import org.zk.simplespring.aop.MethodMatcher;
import org.zk.simplespring.aop.Pointcut;
import org.zk.simplespring.aop.PointcutAdvisor;
import org.zk.simplespring.aop.framework.ProxyFactory;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.BeanFactoryAware;
import org.zk.simplespring.beans.factory.config.BeanPostProcessor;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractAdvisorAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {

	public static final Logger log = LoggerFactory.getLogger(AbstractAdvisorAutoProxyCreator.class);

	protected DefaultListableBeanFactory beanFactory;


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
		if (isAdvisor(aClass)) {
			return Collections.emptyList();
		}
		List<Advisor> advisors = findCandidateAdvisor();
		advisors = findAdvisorsThatCanApply(aClass, advisors);
		return advisors;
	}

	protected boolean isAdvisor(Class<?> aClass) {
		if (Advisor.class.isAssignableFrom(aClass)) {
			return true;
		}
		return false;
	}

	/**
	 * 找到所有增强器
	 * @return
	 */
	protected List<Advisor> findCandidateAdvisor() {
		// TODO 找实现Advisor接口的bean
		return new ArrayList<>();
	}

	/**
	 * 过滤出可以应用的增强器，遍历被代理类所有接口的所有方法，只要有1个方法满足切点表达式则需要增强
	 * <p>在代理执行时，还会再次判断切点表达式是否符合，符合的才调用增强方法</p>
	 * @see org.zk.simplespring.aop.framework.JdkDynamicAopProxy#match(Method, Advisor)
	 * @param aClass
	 * @param advisors
	 * @return
	 */
	private List<Advisor> findAdvisorsThatCanApply(Class<?> aClass, List<Advisor> advisors) {
		List<Advisor> canApplyAdvisors = new ArrayList<>();
		for (Advisor advisor : advisors) {
			if (canApply(aClass, advisor)) {
				canApplyAdvisors.add(advisor);
			}
		}
		return canApplyAdvisors;
	}

	private boolean canApply(Class<?> aClass, Advisor advisor) {
		if (advisor instanceof PointcutAdvisor) {
			Pointcut pointcut = ((PointcutAdvisor) advisor).getPointcut();
			MethodMatcher methodMatcher = pointcut.getMethodMatcher();
			Class[] interfaces = aClass.getInterfaces();
			for (Class interface_ : interfaces) {
				Method[] methods = interface_.getDeclaredMethods();
				for (Method method : methods) {
					if (methodMatcher.matches(method, aClass)) {
						return true;
					}
				}
			}
			return false;
		} else {
			return true;
		}
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
