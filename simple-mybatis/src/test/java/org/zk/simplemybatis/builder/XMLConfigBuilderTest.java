package org.zk.simplemybatis.builder;


import org.junit.Assert;
import org.junit.Test;
import org.zk.simplemybatis.Configuration;

import java.io.InputStream;

public class XMLConfigBuilderTest {

    @Test
    public void parse() {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis.xml");
        Configuration configuration = new XMLConfigBuilder(in).parse();

    }
}
