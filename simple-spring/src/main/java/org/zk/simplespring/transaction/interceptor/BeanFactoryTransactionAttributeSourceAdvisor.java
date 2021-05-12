package org.zk.simplespring.transaction.interceptor;

import org.aopalliance.aop.Advice;
import org.zk.simplespring.aop.Pointcut;
import org.zk.simplespring.aop.PointcutAdvisor;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.BeanFactoryAware;

/**
 * 事务Advisor
 */
public class BeanFactoryTransactionAttributeSourceAdvisor implements PointcutAdvisor, BeanFactoryAware {

	private String adviceBeanName; // 解析标签时TransactionInterceptor的beanName

	private Advice advice;

	// private TransactionAttributeSource transactionAttributeSource; // 解析标签时注入的，提供@Transactional标签的属性

	private final TransactionAttributeSourcePointcut pointcut = new TransactionAttributeSourcePointcut();

	private BeanFactory beanFactory;


	@Override
	public Pointcut getPointcut() {
		return pointcut;
	}

	@Override
	public Advice getAdvice() {
		if (advice != null) {
			return advice;
		}
		this.advice = (Advice) this.beanFactory.getBean(this.adviceBeanName);
		return advice;
	}

	public void setAdviceBeanName(String adviceBeanName) {
		this.adviceBeanName = adviceBeanName;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
}
