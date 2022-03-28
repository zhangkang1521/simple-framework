package org.zk.simplespring.beans.factory;

import org.zk.simplespring.beans.PropertyValue;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.config.BeanFactoryPostProcessor;
import org.zk.simplespring.beans.factory.config.TypedStringValue;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.core.io.support.PropertiesLoaderUtils;
import org.zk.simplespring.util.StringValueResolver;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 替换BeanDefinition占位符，是一个BeanFactory后置处理器
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {


    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";


    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;

    @Override
    public void postProcessBeanFactory(DefaultListableBeanFactory beanFactory) {
        // 加载属性文件
        Properties properties = PropertiesLoaderUtils.loadProperties(location);
        // 遍历beanDefinition
        Map<String, BeanDefinition> beanDefinitionMap = beanFactory.getBeanDefinitionMap();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            List<PropertyValue> propertyValues = beanDefinition.getPropertyValueList();
            for (PropertyValue propertyValue : propertyValues) {
                Object value = propertyValue.getValue();
                // TypedStringValue 才需要替换
                if (!(value instanceof TypedStringValue)) {
                    continue;
                }
                String strVal = ((TypedStringValue) value).getValue();
                resolveStringValue(strVal, properties);
            }
        });

        // 向容器中添加字符串解析器，供解析@Value注解使用
        StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
        beanFactory.addEmbeddedValueResolver(valueResolver);
    }

    private String resolveStringValue(String strVal, Properties properties) {
        StringBuilder buffer = new StringBuilder(strVal);
        int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        int stopIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
        // ${xx} 的进行替换
        if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
            String propKey = strVal.substring(startIdx + 2, stopIdx);
            String propVal = properties.getProperty(propKey);
            buffer.replace(startIdx, stopIdx + 1, propVal);
        }
        return buffer.toString();
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String strVal) {
            return PropertyPlaceholderConfigurer.this.resolveStringValue(strVal, properties);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
