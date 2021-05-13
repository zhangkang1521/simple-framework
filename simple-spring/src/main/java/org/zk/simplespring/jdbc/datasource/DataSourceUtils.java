package org.zk.simplespring.jdbc.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DataSourceUtils {

	public static final Logger log = LoggerFactory.getLogger(DataSourceUtils.class);

	public static Connection getConnection(DataSource dataSource) {
		// 事务管理器绑定到线程中的连接
		Connection connection = TransactionSynchronizationManager.getResource();
		if (connection != null) {
			return connection;
		}
		try {
			connection = dataSource.getConnection();
			log.info("获取数据库连接 [{}]", connection);
		} catch (SQLException e) {
			throw new RuntimeException("获取数据库连接失败", e);
		}
		return connection;
	}
}
