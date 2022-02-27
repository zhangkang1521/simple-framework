package org.zk.simplespring.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * classpath下资源
 * @author zhangkang
 * @create 2022/2/27 17:57
 */
public class ClassPathResource implements Resource {

	private String path;

	private ClassLoader classLoader;

	public ClassPathResource(String path) {
		this.path = path;
		classLoader = Thread.currentThread().getContextClassLoader();
	}

	@Override
	public InputStream getInputStream() {
		InputStream is = classLoader.getResourceAsStream(path);
		if (is == null) {
			throw new RuntimeException(
					this.path + " cannot be opened because it does not exist");
		}
		return is;
	}
}
