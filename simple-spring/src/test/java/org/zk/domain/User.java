package org.zk.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.beans.factory.BeanNameAware;
import org.zk.simplespring.beans.factory.DisposableBean;
import org.zk.simplespring.beans.factory.InitializingBean;
import org.zk.simplespring.beans.factory.annotation.Autowired;
import org.zk.simplespring.beans.factory.annotation.Value;
import org.zk.simplespring.context.ApplicationContextAware;
import org.zk.simplespring.context.support.ApplicationContext;

public class User implements InitializingBean, DisposableBean, BeanNameAware, ApplicationContextAware {

	private static Logger log = LoggerFactory.getLogger(User.class);

	private int id;

	@Value("${username}")
	private String username;
	@Autowired
	private Order order;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public void afterPropertiesSet() {
		System.out.println("ok");
	}

	@Override
	public void destroy() {
		log.info(">>>> destroy");
	}

	@Override
	public void setBeanName(String beanName) {
		System.out.println(beanName);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		System.out.println(applicationContext);
	}
}
