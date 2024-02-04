package org.zk.rocketmq.remoting.netty;

import io.netty.channel.ChannelHandlerContext;
import org.zk.rocketmq.remoting.protocol.RemotingCommand;

/**
 * 请求处理器
 * @author zhangkang
 * @date 2023/5/3 20:51
 */
public interface NettyRequestProcessor {

    /**
     * 处理请求
     * @param ctx
     * @param request
     * @return
     */
    RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request);
}
