package org.zk.simplemybatis.mapping;

import org.zk.simplemybatis.transaction.TransactionFactory;

import javax.sql.DataSource;

/**
 * 包含DataSource和TransactionFactory
 */
public class Environment {

	private final TransactionFactory transactionFactory;
	private final DataSource dataSource;

	public Environment(TransactionFactory transactionFactory, DataSource dataSource) {
		this.transactionFactory = transactionFactory;
		this.dataSource = dataSource;
	}

	public TransactionFactory getTransactionFactory() {
		return transactionFactory;
	}

	public DataSource getDataSource() {
		return dataSource;
	}
}
