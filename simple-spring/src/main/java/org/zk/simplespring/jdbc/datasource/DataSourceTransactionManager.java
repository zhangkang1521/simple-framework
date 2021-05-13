package org.zk.simplespring.jdbc.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务管理器
 */
public class DataSourceTransactionManager {

	public static final Logger log = LoggerFactory.getLogger(DataSourceTransactionManager.class);

	private DataSource dataSource;

	public void getTransaction() {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			log.info("获取数据库连接 [{}]", connection);
			connection.setAutoCommit(false);
			log.info("connection set auto commit to false");
		} catch (SQLException e) {
			throw new RuntimeException("获取数据库连接失败", e);
		}
		TransactionSynchronizationManager.bindResource(connection);
	}

	public void commit() {
		Connection connection = TransactionSynchronizationManager.getResource();
		try {
			connection.commit();
			log.info("提交事务[{}]", connection);
		} catch (SQLException e) {
			throw new RuntimeException("提交事务失败", e);
		} finally {
			close(connection);
		}
	}

	public void rollback() {
		Connection connection = TransactionSynchronizationManager.getResource();
		try {
			connection.rollback();
			log.info("回滚事务[{}]", connection);
		} catch (SQLException e) {
			throw new RuntimeException("回滚事务失败", e);
		} finally {
			close(connection);
		}
	}

	private void close(Connection connection) {
		try {
			TransactionSynchronizationManager.clearResource();
			log.info("关闭连接[{}]", connection);
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException("关闭数据库连接失败", e);
		}
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
