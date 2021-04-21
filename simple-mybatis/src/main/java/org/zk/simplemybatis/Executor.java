package org.zk.simplemybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Executor {

    public static final Logger log = LoggerFactory.getLogger(Executor.class);

    private Configuration configuration;
    private Connection connection;

    public Executor(Connection connection, Configuration configuration) {
        this.connection = connection;
        this.configuration = configuration;
    }

    public <E> List<E> query(MappedStatement ms, Object parameter) throws SQLException {
        StatementHandler statementHandler = configuration.newStatementHandler(ms);
        PreparedStatement statement = statementHandler.prepare(connection);
        statementHandler.parameterize(statement);
        return statementHandler.query(statement);
    }

    public void close() {
        try {
            this.connection.close();
            log.info("关闭数据库连接");
        } catch (SQLException e) {
            log.error("关闭数据库连接异常", e);
        }
    }
}
