package org.zk.simplespring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * spring资源管理统一接口
 * @author zhangkang
 * @create 2022/2/27 17:55
 */
public interface Resource {

	/**
	 * 获取资源的InputStream
	 * @return
	 * @throws IOException
	 */
	InputStream getInputStream();
}
