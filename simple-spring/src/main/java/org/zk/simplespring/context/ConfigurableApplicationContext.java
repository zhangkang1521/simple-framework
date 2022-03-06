package org.zk.simplespring.context;

import org.zk.simplespring.context.support.ApplicationContext;

public interface ConfigurableApplicationContext extends ApplicationContext {

    void refresh();
}
