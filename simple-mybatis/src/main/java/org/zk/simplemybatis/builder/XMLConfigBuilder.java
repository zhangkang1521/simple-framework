package org.zk.simplemybatis.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.zk.simplemybatis.Configuration;
import org.zk.simplemybatis.io.Resources;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;

/**
 * mybatis配置文件解析
 */
public class XMLConfigBuilder {

    private static final Logger log = LoggerFactory.getLogger(XMLConfigBuilder.class);

    private InputStream configInputStream;

    public XMLConfigBuilder(InputStream configInputStream) {
        this.configInputStream = configInputStream;
    }

    public Configuration parse() {
        log.info("加载配置文件");
        Configuration configuration = new Configuration();
        parseMappers(configuration);
        // 解析其他标签...
        return configuration;
    }

    private void parseMappers(Configuration configuration) {
        log.info("开始解析mappers标签");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(configInputStream);
            XPath xPath = XPathFactory.newInstance().newXPath();
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
}
