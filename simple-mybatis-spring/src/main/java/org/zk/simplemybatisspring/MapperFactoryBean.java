package org.zk.simplemybatisspring;

import org.zk.simplemybatis.SqlSession;
import org.zk.simplemybatis.SqlSessionFactory;
import org.zk.simplespring.beans.factory.FactoryBean;

public class MapperFactoryBean<T> implements FactoryBean<T> {

	private SqlSessionFactory sqlSessionFactory;
	private Class<T> mapperInterface;
	private SqlSession sqlSession;

	@Override
	public T getObject() {
		return sqlSession.getMapper(mapperInterface);
	}

	public Class<?> getObjectType() {
		return mapperInterface;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		sqlSession = sqlSessionFactory.openSession();
	}

	public void setMapperInterface(Class<T> mapperInterface) {
		this.mapperInterface = mapperInterface;
	}
}
