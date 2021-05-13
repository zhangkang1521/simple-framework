package org.zk.simplemybatisspring.transaction;

import org.zk.simplemybatis.transaction.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SpringManagedTransaction implements Transaction {

	private DataSource dataSource;

	public SpringManagedTransaction(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO
		return dataSource.getConnection();
	}

	@Override
	public void commit() throws SQLException {

	}

	@Override
	public void rollback() throws SQLException {

	}

	@Override
	public void close() throws SQLException {

	}
}
