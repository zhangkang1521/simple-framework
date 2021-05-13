package org.zk.simplemybatisspring.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplemybatis.transaction.Transaction;
import org.zk.simplespring.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SpringManagedTransaction implements Transaction {

	public static final Logger log = LoggerFactory.getLogger(SpringManagedTransaction.class);

	private DataSource dataSource;
	private Connection connection;

	public SpringManagedTransaction(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Connection getConnection() throws SQLException {
		connection = DataSourceUtils.getConnection(dataSource);
		return connection;
	}

	@Override
	public void commit() throws SQLException {
		log.info("提交[{}]", connection);
		connection.commit();
	}

	@Override
	public void rollback() throws SQLException {
		log.info("回滚[{}]", connection);
		connection.rollback();
	}

	@Override
	public void close() throws SQLException {
		log.info("关闭连接[{}]", connection);
		connection.close();
	}
}
