package org.zk.dubbo.remoting.transport.netty4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private Object response;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("收到服务端消息:{}", msg);
        this.response = msg;
    }


}
