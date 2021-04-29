package org.zk.simplespring.core.io.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoaderUtils {

	public static Properties loadProperties(String classPathLocation) {
		Properties properties = new Properties();
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(classPathLocation);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("加载配置文件错误", e);
		}
		return properties;
	}
}
