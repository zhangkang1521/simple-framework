package org.zk.simplemybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplemybatis.binding.MapperProxy;

import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.List;

public class SqlSession {

    public static final Logger log = LoggerFactory.getLogger(SqlSession.class);

    private Configuration configuration;
    private Executor executor;

    public SqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public <E> List<E> selectList(String statement, Object parameter) {
        log.info("根据{}获取MappedStatement", statement);
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        if (mappedStatement == null) {
            throw new RuntimeException("未找到MappedStatement" + statement);
        }
        try {
            return executor.query(mappedStatement, parameter);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getMapper(Class<T> mapperInterface) {
        return (T)Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, new MapperProxy(this));
    }

    public void close() {
        executor.close();
    }

    public void commit() {
        executor.commit();
    }
}
