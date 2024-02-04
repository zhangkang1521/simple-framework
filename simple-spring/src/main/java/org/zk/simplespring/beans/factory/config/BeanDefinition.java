package org.zk.simplespring.beans.factory.config;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import org.zk.simplespring.beans.PropertyValue;
import org.zk.simplespring.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanDefinition {
	private Object beanClass; // String或者类
	private List<PropertyValue> propertyValueList = new ArrayList<>();

	// 注解方式使用，如果有值，则使用工厂方法创建bean
	private String factoryBeanName;
	private Method factoryMethod;


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

	public String getFactoryBeanName() {
		return factoryBeanName;
	}

	public void setFactoryBeanName(String factoryBeanName) {
		this.factoryBeanName = factoryBeanName;
	}

	public Method getFactoryMethod() {
		return factoryMethod;
	}

	public void setFactoryMethod(Method factoryMethod) {
		this.factoryMethod = factoryMethod;
	}

	@Override
	public String toString() {
		return JSONUtil.toJsonStr(this);
	}
}
