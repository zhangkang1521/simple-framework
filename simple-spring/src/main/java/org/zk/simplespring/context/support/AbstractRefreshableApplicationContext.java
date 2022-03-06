package org.zk.simplespring.context.support;

import org.zk.simplespring.beans.factory.support.DefaultListableBeanFactory;

/**
 * 负责创建bean工厂
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() {
        beanFactory = new DefaultListableBeanFactory();
        loadBeanDefinitions(beanFactory);
    }

    /**
     * 子类AbstractXmlApplicationContext实现
     * @param beanFactory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    protected DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
