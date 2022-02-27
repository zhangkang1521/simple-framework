package org.zk.simplespring.core.io;

import java.io.*;

/**
 * 文件系统资源
 * @author zhangkang
 * @create 2022/2/27 18:15
 */
public class FileSystemResource implements Resource {

	private final File file;

	private final String path;

	public FileSystemResource(File file) {
		this.file = file;
		this.path = file.getPath();
	}

	public FileSystemResource(String path) {
		this.file = new File(path);
		this.path = path;
	}

	@Override
	public InputStream getInputStream() {
		try {
			return new FileInputStream(this.file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public final String getPath() {
		return this.path;
	}
}
