package org.zk.simple.spring.boot.autoconfigure.web.tomcat;

import lombok.SneakyThrows;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.zk.simple.spring.boot.autoconfigure.web.servlet.ServletContextInitializer;

import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.Set;

/**
 * @author zhangkang
 * @date 2024/3/22 10:44
 */
public class TomcatServletWebServerFactory {

    private static final Set<Class<?>> NO_CLASSES = Collections.emptySet();

    @SneakyThrows
    public Tomcat getWebServer(ServletContextInitializer... initializers) {
        Tomcat tomcat = new Tomcat();

        tomcat.setBaseDir("E:/tmp/tomcat");
        // connector 配置端口
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(9999);
        tomcat.getService().addConnector(connector);
        // context
        TomcatEmbeddedContext context = new TomcatEmbeddedContext();

        tomcat.getHost().addChild(context);
        // 配置
        TomcatStarter starter = new TomcatStarter();
        context.addServletContainerInitializer(starter, NO_CLASSES);
        // 启动
        tomcat.start();
        return tomcat;
    }


}
