package org.zk.simplespring.transaction.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.jdbc.datasource.DataSourceTransactionManager;

/**
 * 事务方法拦截
 */
public class TransactionInterceptor implements MethodInterceptor {

	public static final Logger log = LoggerFactory.getLogger(TransactionInterceptor.class);

	private DataSourceTransactionManager transactionManager;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		transactionManager.getTransaction();
		Object result = null;
		try {
			result = invocation.proceed();
		} catch (Throwable e) {
			transactionManager.rollback();
			throw e;
		}
		transactionManager.commit();
		return result;
	}

	public void setTransactionManager(DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}
