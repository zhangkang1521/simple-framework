package org.zk.dubbo.common;

import org.junit.Test;

import static org.junit.Assert.*;

public class URLTest {

    @Test
    public void valueOf() {
        URL url = URL.valueOf("dubbo://localhost:20881");
        assertEquals("dubbo", url.getProtocol());
        assertEquals("localhost", url.getHost());
        assertEquals(20881, url.getPort());
    }
}