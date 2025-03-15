package org.zk.simple.spring.boot.autoconfigure.web.tomcat;

import org.apache.catalina.Container;
import org.apache.catalina.core.StandardContext;

/**
 * @author zhangkang
 * @date 2024/3/23 22:04
 */
public class TomcatEmbeddedContext extends StandardContext {

    @Override
    public boolean loadOnStartup(Container[] children) {
        // 重写父类方法，不做任何实现
        return true;
    }
}
