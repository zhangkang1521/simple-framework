package org.zk.simplespring.context.support;

import org.zk.simplespring.beans.factory.ListableBeanFactory;
import org.zk.simplespring.context.ApplicationEventPublisher;

/**
 *
 */
public interface ApplicationContext extends ListableBeanFactory, ApplicationEventPublisher {
}
