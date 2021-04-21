package org.zk.simplemybatis.builder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.zk.simplemybatis.Configuration;
import org.zk.simplemybatis.MappedStatement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;

/**
 * Mapper文件解析
 */
public class XMLMapperBuilder {

    private Configuration configuration;
    private InputStream mapperInputSteam;

    public XMLMapperBuilder(Configuration configuration, InputStream mapperInputSteam) {
        this.configuration = configuration;
        this.mapperInputSteam = mapperInputSteam;
    }

    public void parse() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(mapperInputSteam);
            XPath xPath = XPathFactory.newInstance().newXPath();
            String namespace = (String)xPath.evaluate("/mapper/@namespace", doc, XPathConstants.STRING);
            // 暂时只考虑select
            NodeList nodeList = (NodeList) xPath.evaluate("/mapper/select", doc, XPathConstants.NODESET);
            for (int i = 1; i <= nodeList.getLength(); i++) {
                String id = (String)xPath.evaluate("/mapper/select[" + i + "]/@id", doc, XPathConstants.STRING);
                String resultType = (String)xPath.evaluate("/mapper/select[" + i + "]/@resultType", doc, XPathConstants.STRING);
                String sql = (String)xPath.evaluate("/mapper/select[" + i + "]", doc, XPathConstants.STRING);
                MappedStatement mappedStatement = new MappedStatement();
                mappedStatement.setId(namespace + "." + id);
                mappedStatement.setResultType(Class.forName(resultType));
                mappedStatement.setSourceSql(sql.trim());
                configuration.addMappedStatement(mappedStatement);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
