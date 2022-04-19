package org.zk.dubbo.remoting.transport.netty4;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;
import org.zk.dubbo.config.ReferenceConfig;
import org.zk.dubbo.rpc.RpcInvocation;

@Slf4j
public class NettyClient {

    private ReferenceConfig referenceConfig;

    private Channel channel;

    public NettyClient(ReferenceConfig referenceConfig, NettyClientHandler nettyClientHandler) {
        this.referenceConfig = referenceConfig;

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast("encoder", new ObjectEncoder())
                                    .addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))
                                    .addLast(nettyClientHandler);
                        }
                    });
            this.channel = bootstrap.connect(referenceConfig.getHost(), referenceConfig.getPort()).sync().channel();
            log.info("connect to server success, host:{}, port:{}", referenceConfig.getHost(), referenceConfig.getPort());

//            this.channel.writeAndFlush(rpcInvocation);
//            this.channel.writeAndFlush(rpcInvocation);
//             channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // group.shutdownGracefully();
        }
    }

    public void send(RpcInvocation rpcInvocation) {
        try {
            log.info("发送请求到服务端, {}", rpcInvocation);
            // 如何拿到返回值
            ChannelFuture future = this.channel.writeAndFlush(rpcInvocation);
            future.await(3000);
            // 没有这句，还没收到服务端消息就返回了
            // 放开这句，2次请求，只有第一次请求发送出去了
            channel.closeFuture().sync();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


}
