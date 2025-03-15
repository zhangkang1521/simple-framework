package org.zk.simpledemo;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.zk.simple.spring.web.context.support.AnnotationConfigWebApplicationContext;
import org.zk.simpledemo.config.AppConfig;

public class DemoApplication {



	public static void main(String[] args) throws Exception {
		new AnnotationConfigWebApplicationContext(AppConfig.class);

		System.in.read();
	}
}
