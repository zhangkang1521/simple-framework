package org.zk.simplespring.core.io;

/**
 * @author zhangkang
 * @create 2022/2/27 19:20
 */
public interface ResourceLoader {

	String CLASSPATH_URL_PREFIX = "classpath:";

	Resource getResource(String location);
}
