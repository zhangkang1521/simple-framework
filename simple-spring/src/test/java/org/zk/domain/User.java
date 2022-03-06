package org.zk.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.beans.factory.DisposableBean;
import org.zk.simplespring.beans.factory.InitializingBean;

public class User implements InitializingBean, DisposableBean {

	private static Logger log = LoggerFactory.getLogger(User.class);

	private int id;
	private String username;
//	@Autowired
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
}
