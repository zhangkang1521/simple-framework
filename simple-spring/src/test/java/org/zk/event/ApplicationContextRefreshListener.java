package org.zk.event;

import org.zk.simplespring.context.ApplicationListener;
import org.zk.simplespring.context.event.ContextRefreshedEvent;

public class ApplicationContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println(">>> context refreshed event >>>");
    }
}
