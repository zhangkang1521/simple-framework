package org.zk.rocketmq.remoting.protocol;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
     * @see org.zk.rocketmq.common.protocol.RequestCode
     */
    private int code;

    /**
     * 消息头
     */
    private Map<String, Object> header = new HashMap<>();

    /**
     * 消息体
     */
    private byte[] body;
}
