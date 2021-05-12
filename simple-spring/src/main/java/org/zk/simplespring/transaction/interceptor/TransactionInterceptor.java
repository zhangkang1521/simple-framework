package org.zk.simplespring.transaction.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * 事务方法拦截
 */
public class TransactionInterceptor implements MethodInterceptor {

	public static final Logger log = LoggerFactory.getLogger(TransactionInterceptor.class);

	// TODO 替换成事务管理器
	private DataSource dataSource;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		log.info("获取数据库事务");
		Object result = null;
		try {
			result = invocation.proceed();
		} catch (Exception e) {
			log.error("回滚数据库事务", e);

		}
		log.info("提交数据库事务");
		return result;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
