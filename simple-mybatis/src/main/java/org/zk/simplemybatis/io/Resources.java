package org.zk.simplemybatis.io;

import java.io.InputStream;

public class Resources {

    private static ClassLoader systemClassLoader;

    static {
        systemClassLoader = ClassLoader.getSystemClassLoader();
    }

    public static InputStream getResourceAsStream(String name) {
        return systemClassLoader.getResourceAsStream(name);
    }
}
