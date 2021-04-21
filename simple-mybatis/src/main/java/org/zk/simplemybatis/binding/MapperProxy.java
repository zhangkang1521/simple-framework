package org.zk.simplemybatis.binding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplemybatis.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Mapper接口动态代理
 */
public class MapperProxy implements InvocationHandler {

    public static final Logger log = LoggerFactory.getLogger(MapperProxy.class);

    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class mapperInterfaceClz = method.getDeclaringClass();
        if (mapperInterfaceClz.equals(Object.class)) {
            return method.invoke(this, args);
        }
        log.info("invoke interface {} method {}", mapperInterfaceClz.getName(), method.getName());
        // TODO 不同的sql需要调用不同的方法
        return sqlSession.selectList(mapperInterfaceClz.getName() + "." + method.getName(), args);
    }
}
