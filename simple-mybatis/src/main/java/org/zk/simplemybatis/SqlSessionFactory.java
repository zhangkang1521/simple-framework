package org.zk.simplemybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zk", "root", "123456");
            log.info("获取数据库连接成功");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Executor executor = configuration.newExecutor(connection);
        return new SqlSession(configuration, executor);
    }
}
