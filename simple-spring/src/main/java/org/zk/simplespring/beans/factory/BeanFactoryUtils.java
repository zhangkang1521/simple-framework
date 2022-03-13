package org.zk.simplespring.beans.factory;

public abstract class BeanFactoryUtils {

    /**
     * 如果带有&前缀则去除
     * @param beanName
     * @return
     */
    public static String transformedBeanName(String beanName) {
        if (isFactoryDereference(beanName)) {
            return beanName.substring(1);
        }
        return beanName;
    }

    /**
     * 是否是FactoryBean前缀,如&user
     * @param name
     * @return
     */
    public static boolean isFactoryDereference(String name) {
        return (name != null && name.startsWith(BeanFactory.FACTORY_BEAN_PREFIX));
    }
}
