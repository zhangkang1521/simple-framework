package org.zk.simple.mybatis.spring.boot.autoconfigure;

import org.zk.simplemybatisspring.SqlSessionFactoryBean;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.BeanFactoryAware;
import org.zk.simplespring.context.annotation.Bean;
import org.zk.simplespring.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MybatisAutoConfiguration implements BeanFactoryAware {

	private BeanFactory beanFactory;

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// 有循环依赖问题，暂时这样处理
		sqlSessionFactoryBean.setDataSource((DataSource) beanFactory.getBean("dataSource"));
		sqlSessionFactoryBean.setMapperLocation("mappers");
		return sqlSessionFactoryBean;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
}
