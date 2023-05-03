package org.zk.rocketmq.remoting.netty;

import cn.hutool.core.util.SerializeUtil;
import cn.hutool.json.JSONUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.zk.rocketmq.remoting.protocol.RemotingCommand;

/**
 * Netty客户端
 *
 * @author zhangkang
 * @date 2023/2/28 14:18
 */
@Slf4j
public class NettyRemotingClient {

    private Channel channel;


    public NettyRemotingClient() {
    }

    @SneakyThrows
    public void start() {
        // Netty-client 启动
        Bootstrap bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(
                                // 入站-解码
                                new NettyDecoder(),
                                // 出站-编码
                                new NettyEncoder(),
                                // 入站 - 接收服务器response
                                new NettyClientHandler());
                    }
                });

        this.channel = bootstrap.connect("localhost", 9999)
                .sync().channel();
    }

    class NettyClientHandler extends SimpleChannelInboundHandler<RemotingCommand> {

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, RemotingCommand remotingCommand) throws Exception {
            log.info("收到服务器返回 {}", remotingCommand);
        }
    }

    public void invokeSync(RemotingCommand request) {
        log.info("发送请求 {}", JSONUtil.toJsonStr(request));
        channel.writeAndFlush(request);
    }

}
