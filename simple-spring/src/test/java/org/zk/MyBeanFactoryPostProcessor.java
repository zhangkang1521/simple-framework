package org.zk;

import org.zk.simplespring.beans.PropertyValue;
import org.zk.simplespring.beans.factory.config.BeanDefinition;
import org.zk.simplespring.beans.factory.config.BeanFactoryPostProcessor;
import org.zk.simplespring.beans.factory.config.TypedStringValue;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

import java.util.List;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(DefaultListableBeanFactory beanFactory) {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("user");
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValueList();

        propertyValues.add(new PropertyValue("username", new TypedStringValue("MyBeanFactoryPostProcessor add!!")));
    }
}
