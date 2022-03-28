package org.zk.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TargetImpl2 {

	public static final Logger log = LoggerFactory.getLogger(TargetImpl2.class);

	public String sayHello(String msg) {
		log.info("invoke sayHello, arg:{}", msg);
		return "hello," + msg;
	}

	public String sayWorld(String msg) {
		log.info("invoke sayWorld, arg:{}", msg);
		return "world," + msg;
	}
}
