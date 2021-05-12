package org.zk.simplemybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * mybatis事务管理接口
 * 默认使用JdbcTransaction，spring中使用ManagedTransaction
 */
public interface Transaction {

	Connection getConnection() throws SQLException;

	void commit() throws SQLException;

	void rollback() throws SQLException;

	void close() throws SQLException;

}
