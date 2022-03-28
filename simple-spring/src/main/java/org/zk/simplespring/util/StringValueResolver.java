package org.zk.simplespring.util;

public interface StringValueResolver {

    /**
     * 将@Value("${xx}")替换成properties文件中的内容
     * @param strVal
     * @return
     */
    String resolveStringValue(String strVal);
}
