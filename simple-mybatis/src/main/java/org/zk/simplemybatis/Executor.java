package org.zk.simplemybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplemybatis.transaction.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Executor {

    public static final Logger log = LoggerFactory.getLogger(Executor.class);

    private Configuration configuration;
    private Transaction transaction;

    public Executor(Transaction transaction, Configuration configuration) {
        this.transaction = transaction;
        this.configuration = configuration;
    }

    public <E> List<E> query(MappedStatement ms, Object parameter) throws SQLException {
        StatementHandler statementHandler = configuration.newStatementHandler(ms);
        Connection connection = transaction.getConnection();
        PreparedStatement statement = statementHandler.prepare(connection);
        statementHandler.parameterize(statement);
        return statementHandler.query(statement);
    }

    public void close() {
        try {
            this.transaction.close();
        } catch (SQLException e) {
            log.error("关闭数据库连接异常", e);
        }
    }

    public void commit() {
        try {
            this.transaction.commit();
        } catch (SQLException e) {
            log.error("数据库连接提交异常", e);
        }
    }
}
