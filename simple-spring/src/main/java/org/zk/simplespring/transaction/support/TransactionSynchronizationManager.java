package org.zk.simplespring.transaction.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.jdbc.datasource.DataSourceTransactionManager;

import java.sql.Connection;

public abstract class TransactionSynchronizationManager {

	public static final Logger log = LoggerFactory.getLogger(DataSourceTransactionManager.class);

	/** 线程绑定的数据库连接，由事务管理器开启事务时绑定，关闭事务时清除 */
	private static final ThreadLocal<Connection> resource = new ThreadLocal<>();

	public static void bindResource(Connection connection) {
		log.info("绑定数据库连接[{}]到当前线程", connection);
		resource.set(connection);
	}

	public static Connection getResource() {
		return resource.get();
	}

	public static void clearResource() {
		resource.remove();
	}
}
