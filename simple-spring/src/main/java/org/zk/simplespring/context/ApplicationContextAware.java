package org.zk.simplespring.context;

import org.zk.simplespring.beans.factory.Aware;
import org.zk.simplespring.context.support.ApplicationContext;

public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext);
}
