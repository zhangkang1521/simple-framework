package org.zk.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.domain.Order;
import org.zk.simplespring.beans.factory.annotation.Autowired;

public class TargetImpl2 {

	@Autowired
	private Order order;

	public static final Logger log = LoggerFactory.getLogger(TargetImpl2.class);

	private String sayTest() {
		log.info("invoke sayTest, order:{}", order);
		return "aa";
	}

	public String sayHello(String msg) {
		log.info("invoke sayHello, arg:{}, order:{}", msg, order);
		return "hello," + msg;
	}

	public String sayWorld(String msg) {
		log.info("invoke sayWorld, arg:{}", msg);
		return "world," + msg;
	}
}
