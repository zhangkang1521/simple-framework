package org.zk;

import org.zk.domain.User;
import org.zk.simplespring.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if ("user".equals(beanName)) {
            User user = (User)bean;
            user.setUsername("MyBeanPostProcessor modified!!");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
