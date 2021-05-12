package org.zk.simplemybatis.builder;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.zk.simplemybatis.Configuration;
import org.zk.simplemybatis.io.Resources;
import org.zk.simplemybatis.mapping.Environment;
import org.zk.simplemybatis.transaction.jdbc.JdbcTransactionFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * mybatis配置文件解析
 */
public class XMLConfigBuilder {

    private static final Logger log = LoggerFactory.getLogger(XMLConfigBuilder.class);

    private InputStream configInputStream;

    private Document doc;
    private XPath xPath;

    public XMLConfigBuilder(InputStream configInputStream) {
        this.configInputStream = configInputStream;
    }

    public Configuration parse() {
        log.info("加载配置文件");
        Configuration configuration = new Configuration();
        parseEnviroment(configuration);
        parseMappers(configuration);
        // 解析其他标签...
        return configuration;
    }

    private void parseEnviroment(Configuration configuration) {
        final String DATA_SOURCE_PATH = "/configuration/environment/dataSource";
        try {
            XPath xPath = getXPath();
            NodeList nodeList = (NodeList) xPath.evaluate(DATA_SOURCE_PATH + "/*", doc, XPathConstants.NODESET);
            DruidDataSource druidDataSource = new DruidDataSource();
            for (int i = 1; i <= nodeList.getLength(); i++) {
                String name = (String)xPath.evaluate(DATA_SOURCE_PATH + "/property[" + i + "]/@name", doc, XPathConstants.STRING);
                String value = (String)xPath.evaluate(DATA_SOURCE_PATH + "/property[" + i + "]/@value", doc, XPathConstants.STRING);
                if ("driver".equals(name)) {
                    druidDataSource.setDriverClassName(value);
                } else if ("url".equals(name)) {
                    druidDataSource.setUrl(value);
                } else if ("username".equals(name)) {
                    druidDataSource.setUsername(value);
                } else if ("password".equals(name)) {
                    druidDataSource.setPassword(value);
                }
            }
            Environment environment = new Environment(new JdbcTransactionFactory(), druidDataSource);
            configuration.setEnvironment(environment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void parseMappers(Configuration configuration) {
        try {
            XPath xPath = getXPath();
            NodeList nodeList = (NodeList) xPath.evaluate("/configuration/mappers/*", doc, XPathConstants.NODESET);
            for (int i = 1; i <= nodeList.getLength(); i++) {
                String resource = (String)xPath.evaluate("/configuration/mappers/mapper[" + i + "]/@resource", doc, XPathConstants.STRING);
                log.info("加载Mapper文件：{}", resource);
                InputStream mapperInputStream = Resources.getResourceAsStream(resource);
                new XMLMapperBuilder(configuration, mapperInputStream).parse();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private XPath getXPath() throws IOException, SAXException, ParserConfigurationException {
        if (xPath == null) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(configInputStream);
            xPath = XPathFactory.newInstance().newXPath();
        }
        return xPath;
    }
}
