package org.zk.rocketmq.remoting.protocol;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangkang
 * @date 2023/3/5 15:23
 */
public class RemotingCommand {

    private static AtomicInteger requestId = new AtomicInteger(0);

    private int opaque = requestId.getAndIncrement();

    private int code;

    private transient byte[] body;
}
