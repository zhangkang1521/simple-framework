package org.zk.rocketmq.remoting.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.zk.rocketmq.remoting.protocol.RemotingCommand;

/**
 * Netty客户端
 * @author zhangkang
 * @date 2023/2/28 14:18
 */
public class NettyRemotingClient {

    private final Bootstrap bootstrap = new Bootstrap();

    private final EventLoopGroup eventLoopGroupWorker;


    public NettyRemotingClient() {
        this.eventLoopGroupWorker = new NioEventLoopGroup();
    }

    public void start() {
        // Netty-client 启动
        Bootstrap handler = this.bootstrap
                .group(this.eventLoopGroupWorker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(
                                // 接收服务器response
                                new NettyClientHandler());
                    }
                });
    }

    class NettyClientHandler extends SimpleChannelInboundHandler<RemotingCommand> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, RemotingCommand msg) throws Exception {
            // processMessageReceived(ctx, msg);
        }
    }

    public void invokeSync(RemotingCommand request) {
        ChannelFuture channelFuture = this.bootstrap.connect("localhost", 8888);
        channelFuture.channel().writeAndFlush(request).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture f) throws Exception {

            }
        });
    }

}
