package org.zk.rocketmq.remoting.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.zk.rocketmq.remoting.protocol.RemotingCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangkang
 * @date 2023/5/2 22:20
 */
@Slf4j
public class NettyRemotingServer {

    private Map<Integer, NettyRequestProcessor> processorTable = new HashMap<>();

    private ExecutorService sendMessageExecutor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000));

    @SneakyThrows
    public void start() {
        // boss 用于接收新连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 用来处理已经被接收的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 设置group
            serverBootstrap.group(bossGroup, workerGroup)
                    // 设置服务端通道实现类型
                    .channel(NioServerSocketChannel.class)
                    // 用于添加pipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    // 入站-解码
                                    new NettyDecoder(),
                                    // 出站-编码
                                    new NettyEncoder(),
                                    // 入站-处理请求
                                    new NettyServerHandler());
                        }
                    });
            // 绑定端口，启动服务
            ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();
            log.info("NettyServer启动");
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    class NettyServerHandler extends SimpleChannelInboundHandler<RemotingCommand> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, RemotingCommand remotingCommand) throws Exception {
            log.info("收到客户端请求 {}", remotingCommand);
            NettyRequestProcessor nettyRequestProcessor = processorTable.get(remotingCommand.getCode());
            if (nettyRequestProcessor == null) {
                log.error("request code not support");
                return;
            }
            sendMessageExecutor.submit(() -> {
                nettyRequestProcessor.processRequest(ctx, remotingCommand);
                ctx.writeAndFlush(remotingCommand);
            });
        }
    }

    public void registerProcessor(int requestCode, NettyRequestProcessor nettyRequestProcessor) {
        this.processorTable.put(requestCode, nettyRequestProcessor);
    }
}
