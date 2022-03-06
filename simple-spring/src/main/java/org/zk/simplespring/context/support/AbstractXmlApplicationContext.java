package org.zk.simplespring.context.support;

import org.zk.simplespring.beans.factory.support.BeanDefinitionReader;
import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;
import org.zk.simplespring.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 负责加载beanDefinitions
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {


    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        // this继承自DefaultResourceLoader
        beanDefinitionReader.setResourceLoader(this);
        beanDefinitionReader.loadBeanDefinitions(getConfigLocation());
    }

    /**
     * 子类ClassPathXmlApplicationContext实现
     * @return
     */
    protected abstract String getConfigLocation();
}
