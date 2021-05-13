package org.zk.simplemybatisspring;

import org.zk.simplemybatis.Configuration;
import org.zk.simplemybatis.SqlSessionFactory;
import org.zk.simplemybatis.SqlSessionFactoryBuilder;
import org.zk.simplemybatis.builder.XMLConfigBuilder;
import org.zk.simplemybatis.builder.XMLMapperBuilder;
import org.zk.simplemybatis.io.Resources;
import org.zk.simplemybatis.mapping.Environment;
import org.zk.simplemybatisspring.transaction.SpringManagedTransactionFactory;
import org.zk.simplespring.beans.factory.FactoryBean;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory> {

	private String configLocation;

	private DataSource dataSource;

	/** mapper文件目录 */
	private String mapperLocation; // spring在依赖注入的时候会使用convert将目录下的文件全部转换成Resource注入



	@Override
	public SqlSessionFactory getObject() {
		if (this.configLocation != null) {
			InputStream inputStream = Resources.getResourceAsStream(configLocation);
			return new SqlSessionFactoryBuilder().build(inputStream);
		} else {
			Configuration configuration = new Configuration();
			Environment environment = new Environment(new SpringManagedTransactionFactory(), this.dataSource);
			configuration.setEnvironment(environment);
			parseMapperFiles(configuration);
			return new SqlSessionFactory(configuration);
		}
	}

	/**
	 * 扫描并解析Mapper文件
	 * @param configuration
	 */
	private void parseMapperFiles(Configuration configuration) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL resource = classLoader.getResource(mapperLocation);
		if (resource == null) {
			throw new RuntimeException("not found mapperLocation " + mapperLocation);
		}
		File rootDir = new File(resource.getFile());
		if (rootDir.isDirectory()) {
			File[] mapperFiles = rootDir.listFiles();
			for (File mapperFile : mapperFiles) {
				try (InputStream mapperInputStream = new FileInputStream(mapperFile)){
					new XMLMapperBuilder(configuration, mapperInputStream).parse();
				} catch (Exception e) {
					throw new RuntimeException("没有找到mapper文件" + mapperFile.getPath());
				}
			}
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
