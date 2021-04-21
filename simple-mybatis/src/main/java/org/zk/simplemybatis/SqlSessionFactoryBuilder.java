package org.zk.simplemybatis;

import org.zk.simplemybatis.builder.XMLConfigBuilder;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream in) {
        Configuration configuration = new XMLConfigBuilder(in).parse();
        return new SqlSessionFactory(configuration);
    }
}
