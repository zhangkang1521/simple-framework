package org.zk.simplespring;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.zk.simplespring.core.io.*;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author zhangkang
 * @create 2022/2/27 19:26
 */
public class ResourceTest {

	@Test
	public void testClassPathResource() throws Exception {
		Resource resource = new ClassPathResource("bean-factory-1.xml");
		InputStream inputStream = resource.getInputStream();
		List<String> line = IOUtils.readLines(inputStream, Charset.forName("UTF-8"));
	}

	@Test
	public void testURLResource() throws Exception {
		Resource resource = new UrlResource(new URL("https://gitee.com/zhangkang1521/xxl-job/blob/master/pom.xml"));
		InputStream inputStream = resource.getInputStream();
		List<String> line = IOUtils.readLines(inputStream, Charset.forName("UTF-8"));
	}

	@Test
	public void testFileSystemResource() throws Exception {
		Resource resource = new FileSystemResource("src/test/resources/app.properties");
		InputStream inputStream = resource.getInputStream();
		List<String> line = IOUtils.readLines(inputStream, Charset.forName("UTF-8"));
	}

	@Test
	public void testDefaultResourceLoader() throws Exception {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource("src/test/resources/app.properties");
		InputStream inputStream = resource.getInputStream();
		List<String> line = IOUtils.readLines(inputStream, Charset.forName("UTF-8"));
	}
}
