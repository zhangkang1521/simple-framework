package org.zk.rocketmq.remoting.protocol;

import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Netty通信，请求、返回值
 * @author zhangkang
 * @date 2023/3/5 15:23
 */
@Data
public class RemotingCommand implements Serializable {

    private static AtomicInteger requestId = new AtomicInteger(0);

    /**
     * 标识一个请求,以便将来匹配对应的响应
     */
    private int opaque = requestId.getAndIncrement();

    /**
     * 请求码
     */
    private int code;

    /**
     *
     */
    //private transient byte[] body;
}
