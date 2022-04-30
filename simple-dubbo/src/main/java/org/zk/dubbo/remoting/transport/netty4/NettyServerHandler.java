package org.zk.dubbo.remoting.transport.netty4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.zk.dubbo.remoting.exchange.Request;
import org.zk.dubbo.remoting.exchange.Response;
import org.zk.dubbo.rpc.Exporter;
import org.zk.dubbo.rpc.RpcInvocation;

import java.util.Map;

/**
 * Netty服务端Handler
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private Map<String, Exporter<?>> registryMap;

    public NettyServerHandler(Map<String, Exporter<?>> registryMap) {
        this.registryMap = registryMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("服务端收到请求：{}", msg);
        if (msg instanceof Request) {
            Request request = (Request)msg;
            RpcInvocation rpcInvocation = request.getRpcInvocation();

            Exporter<?> exporter = registryMap.get(rpcInvocation.getClassName());
            Object result = exporter.getInvoker().invoke(rpcInvocation);

            Response response = new Response();
            response.setMId(request.getMId());
            response.setMResult(result);

            ctx.channel().writeAndFlush(response);
        }
    }
}
