package org.zk.simplespring.beans.factory.xml;

import org.zk.simplespring.beans.factory.SpringBeanUtils;
import org.zk.simplespring.core.io.support.PropertiesLoaderUtils;
import org.zk.simplespring.util.ClassUtils;

import java.util.Properties;

public class DefaultNamespaceHandlerResolver implements NamespaceHandlerResolver {


	@Override
	public NamespaceHandler resolve(String namespaceUri) {
		Properties properties = PropertiesLoaderUtils.loadProperties("META-INF/spring.handlers");
		String className = (String) properties.get(namespaceUri);
		Class<?> clz = ClassUtils.forName(className);
		NamespaceHandler namespaceHandler = (NamespaceHandler) SpringBeanUtils.instantiateClass(clz);
		namespaceHandler.init();
		return namespaceHandler;
	}
}
