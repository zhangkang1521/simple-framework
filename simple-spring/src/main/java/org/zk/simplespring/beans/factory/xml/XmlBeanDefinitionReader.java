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
import org.zk.simplespring.beans.factory.support.AbstractBeanDefinitionReader;
import org.zk.simplespring.beans.factory.support.BeanDefinitionReader;
import org.zk.simplespring.beans.factory.support.BeanDefinitionRegistry;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.core.io.Resource;
import org.zk.simplespring.core.io.ResourceLoader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * xml BeanDefinition 读取
 * @author zhangkang
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

	public static final Logger log = LoggerFactory.getLogger(XmlBeanDefinitionReader.class);

	public static final String DEFAULT_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

	private static final String SCHEMA_LANGUAGE_ATTRIBUTE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

	private static final String XSD_SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";

	private NamespaceHandlerResolver namespaceHandlerResolver;

	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
		super(registry);
		namespaceHandlerResolver = new DefaultNamespaceHandlerResolver();
	}

	@Override
	public void loadBeanDefinitions(Resource resource) {
		log.info("loadBeanDefinition from classpath {}", resource);
		InputStream inputStream = resource.getInputStream();

		try {
			InputSource inputSource = new InputSource(inputStream);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			factory.setValidating(false);
			factory.setAttribute(SCHEMA_LANGUAGE_ATTRIBUTE, XSD_SCHEMA_LANGUAGE);

			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			// 整个xml文档
			Document doc = dBuilder.parse(inputSource);

			// beans
			Element root = doc.getDocumentElement();
			// beans的子元素
			NodeList noList = root.getChildNodes();
			for (int i = 0; i < noList.getLength(); i++) {
				// text bean text 这3个
				Node node = noList.item(i);
				// 过滤掉text
				if (node instanceof Element) {
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

	@Override
	public void loadBeanDefinitions(String location) {
		// ApplicationContext启动时，是自己
		// BeanFactory不走这里，直接使用外部传入的Resource，无需ResourceLoader
		ResourceLoader resourceLoader = getResourceLoader();
		Resource resource = resourceLoader.getResource(location);
		loadBeanDefinitions(resource);
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
		getRegistry().registerBeanDefinition(beanName, beanDefinition);
	}

	/**
	 * 处理自定义标签，例如<context:component-scan base-package="xxx"/>
	 * @param node
	 */
	private void parseCustomElement(Element node) {
		String namespace = node.getNamespaceURI();
		log.info("解析自定义标签namespace {}", namespace);
		NamespaceHandler namespaceHandler = this.namespaceHandlerResolver.resolve(namespace);
		namespaceHandler.parse(node, getRegistry());
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
