package org.zk.simplespring.core.io;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 默认资源加载器
 * @author zhangkang
 * @create 2022/2/27 19:21
 */
public class DefaultResourceLoader implements ResourceLoader {

	@Override
	public Resource getResource(String location) {
		if (location.startsWith(CLASSPATH_URL_PREFIX)) {
			return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
		}
		else {
			try {
				URL url = new URL(location);
				return new UrlResource(url);
			} catch (MalformedURLException e) {
				// Spring中默认使用ClassPathContextResource
				return new ClassPathResource(location);
			}
		}
	}

}
