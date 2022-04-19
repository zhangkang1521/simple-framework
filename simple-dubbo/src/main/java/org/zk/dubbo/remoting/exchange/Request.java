package org.zk.dubbo.remoting.exchange;

import lombok.Data;
import org.zk.dubbo.rpc.RpcInvocation;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class Request implements Serializable {

    private static final AtomicLong INVOKE_ID = new AtomicLong(0);

    /**
     * 请求消息id，结果返回时Response的mId与此id相同，用于定位唤醒对应的线程
     */
    private Long mId;

    /**
     * rpc调用定位服务
     */
    private RpcInvocation rpcInvocation;

    public Request() {
        this.mId = INVOKE_ID.getAndIncrement();
    }
}
