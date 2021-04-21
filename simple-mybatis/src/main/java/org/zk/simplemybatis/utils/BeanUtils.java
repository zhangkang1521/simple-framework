package org.zk.simplemybatis.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanUtils {

    /**
     * 获取类中字段对应的类型
     * @param cls
     * @param fieldName
     * @return
     */
    public static Class getFieldClass(Class cls, String fieldName) {
        Method method = null;
        try {
            method = cls.getMethod("get" + StringUtils.capitalize(fieldName));
        } catch (NoSuchMethodException e) {
            return null;
        }
        return method != null ? method.getReturnType() : null;
    }

    public static Object newInstance(Class cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    public static <T> void setProperties(Object rowObj, String fieldName, T value) {
        try {
            Method setMethod = rowObj.getClass().getMethod("set" + StringUtils.capitalize(fieldName), value.getClass());
            setMethod.invoke(rowObj, value);
        } catch (Exception e) {
            // ignore
        }
    }
}
