package org.zk.simplespring.transaction.config;

import org.w3c.dom.Element;
import org.zk.simplespring.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.zk.simplespring.aop.config.AopConfigUtils;
import org.zk.simplespring.beans.PropertyValue;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.config.TypedStringValue;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.beans.factory.xml.BeanDefinitionParser;
import org.zk.simplespring.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.zk.simplespring.transaction.interceptor.TransactionInterceptor;

/**
 * <tx:annotation-driven/>标签解析
 * <p>注册AnnotationAwareAspectJAutoProxyCreator进行代理创建
 * （spring框架中使用InfrastructureAdvisorAutoProxyCreator，
 *  但是如果配置有<aop:aspectj-autoproxy/>还是会优先使用AnnotationAwareAspectJAutoProxyCreator）
 * </p>
 * <p>
 *     注册 BeanFactoryTransactionAttributeSourceAdvisor AnnotationTransactionAttributeSource TransactionInterceptor
 *     3个BeanDefinition对事务进行支持
 * </p>
 */
public class AnnotationDrivenBeanDefinitionParser implements BeanDefinitionParser {

	public static final String TRANSACTION_INTERCEPTOR_BEAN_NAME = "transactionInterceptor";

	public static final String TRANSACTION_ADVISOR_BEAN_NAME = "transactionAdvisor";

	@Override
	public BeanDefinition parse(Element element, DefaultListableBeanFactory beanFactory) {
		AopConfigUtils.registerAutoProxyCreatorIfNecessory(beanFactory);

		BeanDefinition transactionInterceptor = new BeanDefinition();
		transactionInterceptor.setBeanClass(TransactionInterceptor.class);
		beanFactory.registerBeanDefinition(TRANSACTION_INTERCEPTOR_BEAN_NAME, transactionInterceptor);

		BeanDefinition transactionAdvisor = new BeanDefinition();
		transactionAdvisor.setBeanClass(BeanFactoryTransactionAttributeSourceAdvisor.class);
		transactionAdvisor.addProperty(new PropertyValue("adviceBeanName", new TypedStringValue(TRANSACTION_INTERCEPTOR_BEAN_NAME)));
		beanFactory.registerBeanDefinition(TRANSACTION_ADVISOR_BEAN_NAME, transactionAdvisor);
		return null;
	}
}
