package org.zk.dubbo.remoting.transport.netty4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;
import org.zk.dubbo.common.URL;
import org.zk.dubbo.rpc.Exporter;

import java.util.Map;

@Slf4j
public class NettyServer {

    private URL url;

    private int port;

    /**
     * 服务暴露
     */
    private Map<String, Exporter<?>> exporterMap;

    public NettyServer(URL url, Map<String, Exporter<?>> exporterMap) {
        this.url = url;
        this.port = url.getPort();
        this.exporterMap = exporterMap;
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast("encoder", new ObjectEncoder())
                                    .addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))
                                    .addLast(new NettyServerHandler(exporterMap));
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            log.info("Netty Server started at port {}",  port);
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
