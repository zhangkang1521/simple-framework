package org.zk.dubbo.remoting.transport.netty4;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.zk.dubbo.common.URL;
import org.zk.dubbo.remoting.exchange.Request;
import org.zk.dubbo.remoting.exchange.support.DefaultFuture;

@Slf4j
public class NettyClient {

    private URL url;

    private Channel channel;

    public NettyClient(URL url, NettyClientHandler nettyClientHandler) {

        NettyCodecAdapter nettyCodecAdapter = new NettyCodecAdapter();

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast("encoder", nettyCodecAdapter.getEncoder())
                                    .addLast("decoder", nettyCodecAdapter.getDecoder())
//                                    .addLast("encoder", new ObjectEncoder())
//                                    .addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))
                                    .addLast(nettyClientHandler);
                        }
                    });
            this.channel = bootstrap.connect(url.getHost(), url.getPort()).sync().channel();
            log.info("connect to server success, host:{}, port:{}", url.getHost(), url.getPort());

//            this.channel.writeAndFlush(rpcInvocation);
//            this.channel.writeAndFlush(rpcInvocation);
//             channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // group.shutdownGracefully();
        }
    }

    public DefaultFuture send(Request request) {
        DefaultFuture defaultFuture = new DefaultFuture(request.getMId());
        try {
            log.info("发送请求到服务端, {}", request);
            channel.writeAndFlush(request);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return defaultFuture;
    }


}
