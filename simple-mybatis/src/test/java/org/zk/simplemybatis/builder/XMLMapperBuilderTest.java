package org.zk.simplemybatis.builder;


import org.junit.Test;
import org.zk.simplemybatis.Configuration;
import org.zk.simplemybatis.io.Resources;

import java.io.InputStream;

public class XMLMapperBuilderTest {


    @Test
    public void parse() {
        InputStream resource = Resources.getResourceAsStream("mappers/UserMapper.xml");
        Configuration configuration = new Configuration();
        new XMLMapperBuilder(configuration, resource).parse();
    }
}
