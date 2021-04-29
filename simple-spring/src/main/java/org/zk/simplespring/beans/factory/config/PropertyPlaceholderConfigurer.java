package org.zk.simplespring.beans.factory.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.simplespring.BeanDefinition;
import org.zk.simplespring.DefaultListableBeanFactory;
import org.zk.simplespring.PropertyValue;
import org.zk.simplespring.TypedStringValue;
import org.zk.simplespring.core.io.support.PropertiesLoaderUtils;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 属性占位符替换为配置文件中配置的值
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

	public static final Logger log = LoggerFactory.getLogger(PropertyPlaceholderConfigurer.class);

	private String location;

	private Properties properties = new Properties();

	@Override
	public void postProcessBeanFactory(DefaultListableBeanFactory beanFactory) {
		log.info("加载配置文件 {}", location);
		loadProperties();
		processProperties(beanFactory);
	}

	private void loadProperties() {
		properties = PropertiesLoaderUtils.loadProperties(location);
	}

	private void processProperties(DefaultListableBeanFactory beanFactory) {
		Map<String, BeanDefinition> beanDefinitionMap = beanFactory.getBeanDefinitionMap();
		beanDefinitionMap.forEach((beanName, beanDefinition) -> {
			List<PropertyValue> propertyValues = beanDefinition.getPropertyValueList();
			for (PropertyValue propertyValue : propertyValues) {
				if (propertyValue.getValue() instanceof TypedStringValue) {
					String value = ((TypedStringValue)propertyValue.getValue()).getValue();
					String resolvedValue = resolveValue(value);
					if (resolvedValue != null) {
						log.debug("替换属性占位符 {}.{} {} -> {}", beanName, propertyValue.getName(), value, resolvedValue);
						((TypedStringValue) propertyValue.getValue()).setValue(resolvedValue);
					}
				} else {
					// TODO
				}
			}
		});
	}

	private String resolveValue(String value) {
		Pattern pattern = Pattern.compile("^\\$\\{(.+)\\}$");
		Matcher matcher = pattern.matcher(value);
		if (matcher.find()) {
			String replace = matcher.group(1);
			return (String)properties.get(replace);
		}
		return null;
	}



	public void setLocation(String location) {
		this.location = location;
	}
}
