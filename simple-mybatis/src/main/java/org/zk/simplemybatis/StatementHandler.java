package org.zk.simplemybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StatementHandler {

    public static final Logger log = LoggerFactory.getLogger(StatementHandler.class);

    private Configuration configuration;
    private MappedStatement mappedStatement;
    private ResultSetHandler resultSetHandler;



    public StatementHandler(MappedStatement mappedStatement, Configuration configuration) {
        this.mappedStatement = mappedStatement;
        this.configuration = configuration;
        this.resultSetHandler = configuration.newResultSetHandler(mappedStatement);
    }

    public PreparedStatement prepare(Connection connection) throws SQLException {
        log.info("prepare statement {}", mappedStatement.getSourceSql());
        return connection.prepareStatement(mappedStatement.getSourceSql());
    }

    public void parameterize(PreparedStatement statement) {
        // TODO 待实现
    }

    public <E> List<E> query(PreparedStatement statement) throws SQLException {
        statement.execute();
        return resultSetHandler.handleResultSets(statement);
    }
}
