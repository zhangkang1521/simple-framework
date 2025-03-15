package org.zk.simple.spring.boot.autoconfigure.web.servlet;

import javax.servlet.ServletContext;

/**
 * 初始化ServletContext，会调用
 * @author zhangkang
 * @date 2024/3/22 10:47
 */
@FunctionalInterface
public interface ServletContextInitializer {

    void onStartup(ServletContext servletContext);
}
