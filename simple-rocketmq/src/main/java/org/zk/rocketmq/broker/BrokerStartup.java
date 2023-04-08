package org.zk.rocketmq.broker;

import org.zk.rocketmq.store.DefaultMessageStore;

/**
 * Broker启动类
 * @author zhangkang
 * @date 2023/4/8 17:06
 */
public class BrokerStartup {

    public static void main(String[] args) {
        new DefaultMessageStore();
    }
}
