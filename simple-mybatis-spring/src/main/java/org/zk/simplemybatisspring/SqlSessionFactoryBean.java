package org.zk.simplemybatisspring;

import org.zk.simplemybatis.Configuration;
import org.zk.simplemybatis.SqlSessionFactory;
import org.zk.simplemybatis.SqlSessionFactoryBuilder;
import org.zk.simplemybatis.builder.XMLConfigBuilder;
import org.zk.simplemybatis.io.Resources;
import org.zk.simplespring.beans.factory.FactoryBean;

import javax.sql.DataSource;
import java.io.InputStream;

public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory> {

	private String configLocation;

	private DataSource dataSource;

	/** mapper文件目录 */
	private String mapperLocation;



	@Override
	public SqlSessionFactory getObject() {
		if (this.configLocation != null) {
			InputStream inputStream = Resources.getResourceAsStream(configLocation);
			return new SqlSessionFactoryBuilder().build(inputStream);
		} else {
			Configuration configuration = new Configuration();

			return new SqlSessionFactory(configuration);
		}
	}

	public Class<?> getObjectType() {
		return SqlSessionFactory.class;
	}

	public void setConfigLocation(String configLocation) {
		this.configLocation = configLocation;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setMapperLocation(String mapperLocation) {
		this.mapperLocation = mapperLocation;
	}
}
