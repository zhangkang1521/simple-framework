package org.zk.simplespring.core.io.support;

import java.util.*;

/**
 * 加载spring.factories文件
 */
public class SpringFactoriesLoader {

	public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";

	public static List<String> loadFactoryNames(Class<?> factoryClass) {
		Map<String, List<String>> map = new HashMap<>();
		// TODO 需要加载所有jar包的spring.factories文件
		Properties properties = PropertiesLoaderUtils.loadProperties(FACTORIES_RESOURCE_LOCATION);
		properties.forEach((k, v) -> {
			String[] values = ((String)v).split(",");
			map.put((String)k, Arrays.asList(values));
		});
		return map.get(factoryClass.getName());
	}
}
