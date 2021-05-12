package org.zk.simplemybatis.transaction.jdbc;

import org.zk.simplemybatis.transaction.Transaction;
import org.zk.simplemybatis.transaction.TransactionFactory;

import javax.sql.DataSource;

public class JdbcTransactionFactory implements TransactionFactory {

	@Override
	public Transaction newTransaction(DataSource dataSource) {
		return new JdbcTransaction(dataSource);
	}
}
