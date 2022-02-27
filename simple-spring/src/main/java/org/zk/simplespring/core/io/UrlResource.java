package org.zk.simplespring.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * URL资源
 * @author zhangkang
 * @create 2022/2/27 19:09
 */
public class UrlResource implements Resource {

	private URL url;

	public UrlResource(URL url) {
		this.url = url;
	}

	@Override
	public InputStream getInputStream() {
		try {
			URLConnection con = this.url.openConnection();
			return con.getInputStream();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
