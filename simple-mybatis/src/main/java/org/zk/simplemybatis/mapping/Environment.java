package org.zk.simplemybatis.mapping;

import org.zk.simplemybatis.transaction.TransactionFactory;

import javax.sql.DataSource;

/**
 * 包含DataSource和TransactionFactory
 */
public class Environment {

	/**
	 * 这里使用接口，是为了方便与spring整合
	 */
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
