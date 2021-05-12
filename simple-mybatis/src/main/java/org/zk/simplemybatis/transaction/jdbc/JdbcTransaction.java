package org.zk.simplemybatis.transaction.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplemybatis.transaction.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTransaction implements Transaction {

	public static final Logger log = LoggerFactory.getLogger(JdbcTransaction.class);

	private Connection connection;
	private DataSource dataSource;

	public JdbcTransaction(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Connection getConnection() throws SQLException {
		if (connection == null) {
			log.info("获取新的数据库连接");
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			log.info("设置数据库连接自动提交为false");
		}
		return connection;
	}

	@Override
	public void commit() throws SQLException {
		log.info("提交事务");
		connection.commit();
	}

	@Override
	public void rollback() throws SQLException {
		log.info("回滚事务");
		connection.rollback();
	}

	@Override
	public void close() throws SQLException {
		log.info("关闭数据库连接");
		connection.close();
	}


}
