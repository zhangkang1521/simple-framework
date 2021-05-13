package org.zk.simplemybatisspring.transaction;

import org.zk.simplemybatis.transaction.Transaction;
import org.zk.simplemybatis.transaction.TransactionFactory;

import javax.sql.DataSource;

public class SpringManagedTransactionFactory implements TransactionFactory {

	@Override
	public Transaction newTransaction(DataSource dataSource) {
		return new SpringManagedTransaction(dataSource);
	}
}
