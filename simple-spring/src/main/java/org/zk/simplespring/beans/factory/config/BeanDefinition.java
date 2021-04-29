package org.zk.simplespring.beans.factory.config;

import org.zk.simplespring.beans.PropertyValue;
import org.zk.simplespring.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinition {
	private Object beanClass; // String或者类
	private List<PropertyValue> propertyValueList = new ArrayList<>();

	public Class<?> resolveBeanClass()  {
		if (beanClass instanceof Class) {
			return (Class)beanClass;
		}
		return ClassUtils.forName((String)beanClass);
	}

	public Object getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Object beanClass) {
		this.beanClass = beanClass;
	}

	public List<PropertyValue> getPropertyValueList() {
		return propertyValueList;
	}

	public void addProperty(PropertyValue property) {
		propertyValueList.add(property);
	}
}
