package org.zk.simple.spring.boot.autoconfigure.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import org.zk.simplespring.context.annotation.Bean;
import org.zk.simplespring.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceAutoConfiguration {

	@Bean
	public DataSource dataSource() {
		// TODO 配置文件
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/zk");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		return dataSource;
	}

}
