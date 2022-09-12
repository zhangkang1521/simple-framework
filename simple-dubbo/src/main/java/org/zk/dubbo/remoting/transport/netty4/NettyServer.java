package org.zk.dubbo.remoting.transport.netty4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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
        NettyCodecAdapter nettyCodecAdapter = new NettyCodecAdapter();

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
//                                    .addLast("encoder", new ObjectEncoder())
//                                    .addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))
                                    .addLast("encoder", nettyCodecAdapter.getEncoder())
                                    .addLast("decoder", nettyCodecAdapter.getDecoder())
                                    .addLast(new NettyServerHandler(exporterMap));
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(port);
            log.info("Netty Server started at port {}",  port);
            // 阻塞线程
            // channelFuture.channel().closeFuture().sync();
            // 不会阻塞线程
            channelFuture.syncUninterruptibly();
        }  finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
        }
    }
}
