package org.zk.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TargetImpl implements Target {

	public static final Logger log = LoggerFactory.getLogger(TargetImpl.class);

	@Override
	public String sayHello(String msg) {
		log.info("invoke sayHello, arg:{}", msg);
		return "hello," + msg;
	}

	@Override
	public String sayWorld(String msg) {
		log.info("invoke sayWorld, arg:{}", msg);
		return "world," + msg;
	}
}
