package org.zk.simpledemo;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class DemoApplication {

	private static int port = 8080;
	private static String contextPath = "/";


	public static void main(String[] args) throws Exception {
		Tomcat tomcat = new Tomcat();

		String baseDir = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		tomcat.setBaseDir(baseDir);
		tomcat.setPort(port);
//		tomcat.setConnector(new Connector());

		tomcat.addWebapp(contextPath, baseDir);
//		tomcat.enableNaming();
		tomcat.start();

		tomcat.getServer().await();
	}
}
