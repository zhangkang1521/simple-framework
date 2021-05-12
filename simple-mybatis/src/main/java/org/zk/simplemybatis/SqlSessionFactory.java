package org.zk.simplemybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplemybatis.mapping.Environment;
import org.zk.simplemybatis.transaction.Transaction;
import org.zk.simplemybatis.transaction.TransactionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlSessionFactory {

    public static final Logger log = LoggerFactory.getLogger(SqlSessionFactory.class);

    private Configuration configuration;

    public SqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession() {
        Environment environment = configuration.getEnvironment();
        TransactionFactory transactionFactory = environment.getTransactionFactory();
        Transaction transaction = transactionFactory.newTransaction(environment.getDataSource());
        Executor executor = configuration.newExecutor(transaction);
        return new SqlSession(configuration, executor);
    }
}
