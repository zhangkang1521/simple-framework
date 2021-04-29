package org.zk.domain;

import org.zk.simplespring.beans.factory.annotation.Autowired;

public class User {

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
}
