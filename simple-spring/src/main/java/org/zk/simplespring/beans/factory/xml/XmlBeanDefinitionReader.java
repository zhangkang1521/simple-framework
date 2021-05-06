package org.zk.simplespring.beans.factory.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.zk.simplespring.beans.PropertyValue;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.config.RuntimeBeanReference;
import org.zk.simplespring.beans.factory.config.TypedStringValue;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class XmlBeanDefinitionReader {

	public static final Logger log = LoggerFactory.getLogger(XmlBeanDefinitionReader.class);

	public static final String DEFAULT_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

	private static final String SCHEMA_LANGUAGE_ATTRIBUTE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

	private static final String XSD_SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";

	private DefaultListableBeanFactory defaultListableBeanFactory;

	private NamespaceHandlerResolver namespaceHandlerResolver;

	public XmlBeanDefinitionReader(DefaultListableBeanFactory defaultListableBeanFactory) {
		this.defaultListableBeanFactory = defaultListableBeanFactory;
		namespaceHandlerResolver = new DefaultNamespaceHandlerResolver();
	}

	public void loadBeanDefinition(String resource) {
		log.info("loadBeanDefinition from classpath {}", resource);
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(resource);

		try {
			InputSource inputSource = new InputSource(inputStream);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			factory.setValidating(false); // 验证
			factory.setAttribute(SCHEMA_LANGUAGE_ATTRIBUTE, XSD_SCHEMA_LANGUAGE);

			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputSource); // 整个xml文档

			Element root = doc.getDocumentElement(); // beans
			NodeList noList = root.getChildNodes(); // beans的子元素
			for (int i = 0; i < noList.getLength(); i++) {
				Node node = noList.item(i); // text bean text 这3个
				if (node instanceof Element) { // 过滤掉text
					if (DEFAULT_NAMESPACE_URI.equals(node.getNamespaceURI())) {
						parseDefaultElement((Element) node);
					} else {
						parseCustomElement((Element) node);
					}
				}
			}
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("xml解析异常", e);
		} catch (SAXException e) {
			throw new RuntimeException("xml解析异常", e);
		} catch (IOException e) {
			throw new RuntimeException("xml解析io异常", e);
		}
	}

	/**
	 * 处理默认标签 <bean></bean>
	 * @param node
	 */
	private void parseDefaultElement(Element node) {
		String beanName = node.getAttribute("id");
		String className = node.getAttribute("class");
		BeanDefinition beanDefinition = new BeanDefinition();
		beanDefinition.setBeanClass(className);
		parseProperty(node, beanDefinition);
		defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition);
	}

	/**
	 * 处理自定义标签，例如<context:component-scan base-package="xxx"/>
	 * @param node
	 */
	private void parseCustomElement(Element node) {
		String namespace = node.getNamespaceURI();
		log.info("解析自定义标签namespace {}", namespace);
		NamespaceHandler namespaceHandler = this.namespaceHandlerResolver.resolve(namespace);
		namespaceHandler.parse(node, defaultListableBeanFactory);
	}



	private void parseProperty(Element element, BeanDefinition beanDefinition) {
		NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element && "property".equals(node.getNodeName())) {
				String name = ((Element) node).getAttribute("name");
				String value = ((Element) node).getAttribute("value");
				if (value != null && value.length() > 0) {
					beanDefinition.getPropertyValueList().add(new PropertyValue(name, new TypedStringValue(value)));
				} else {
					String ref = ((Element) node).getAttribute("ref");
					beanDefinition.getPropertyValueList().add(new PropertyValue(name, new RuntimeBeanReference(ref)));
				}
			}
		}
	}
}
