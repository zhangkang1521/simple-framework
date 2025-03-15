package org.zk.simpledemo;

import org.apache.catalina.startup.Tomcat;

/**
 * @author zhangkang
 * @date 2024/3/22 9:13
 */
public class TomcatTest {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();

        String baseDir = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        tomcat.setBaseDir(baseDir);
        tomcat.setPort(8080);
//		tomcat.setConnector(new Connector());

        tomcat.addWebapp("/", baseDir);
//		tomcat.enableNaming();
        tomcat.start();

        tomcat.getServer().await();
    }
}
