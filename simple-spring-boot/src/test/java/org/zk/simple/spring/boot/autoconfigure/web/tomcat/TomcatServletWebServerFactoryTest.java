package org.zk.simple.spring.boot.autoconfigure.web.tomcat;

import org.junit.Test;

import javax.servlet.ServletContext;

import static org.junit.Assert.*;

public class TomcatServletWebServerFactoryTest {

    @Test
    public void getWebServer() throws Exception {
        new TomcatServletWebServerFactory().getWebServer(this::onStartup);
        System.in.read();
    }

    private void onStartup(ServletContext servletContext) {
        System.out.println("onStartup");
    }
}