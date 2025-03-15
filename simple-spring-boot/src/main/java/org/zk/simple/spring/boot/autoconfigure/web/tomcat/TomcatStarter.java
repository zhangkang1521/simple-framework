package org.zk.simple.spring.boot.autoconfigure.web.tomcat;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * @author zhangkang
 * @date 2024/3/23 22:08
 */
public class TomcatStarter implements ServletContainerInitializer {


    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        // 注册ServletContainerInitializer
        System.out.println("ServletContainerInitializer.onStartup");
    }
}
