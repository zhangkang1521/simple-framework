package org.zk.simplemybatisspring;

import org.zk.simplemybatis.SqlSessionFactory;
import org.zk.simplemybatis.SqlSessionFactoryBuilder;
import org.zk.simplemybatis.io.Resources;
import org.zk.simplespring.beans.factory.FactoryBean;

import java.io.InputStream;

public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory> {

	private String configLocation;

	@Override
	public SqlSessionFactory getObject() {
		InputStream inputStream = Resources.getResourceAsStream(configLocation);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}

	public Class<?> getObjectType() {
		return SqlSessionFactory.class;
	}

	public void setConfigLocation(String configLocation) {
		this.configLocation = configLocation;
	}
}
