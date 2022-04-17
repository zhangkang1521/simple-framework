package org.zk.dubbo.remoting.transport.netty4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.zk.dubbo.rpc.RpcInvocation;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Netty服务端Handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private Map<String, Object> registryMap;

    public NettyServerHandler(Map<String, Object> registryMap) {
        this.registryMap = registryMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务端收到请求：" + msg);
        if (msg instanceof RpcInvocation) {
            RpcInvocation rpcInvocation = ((RpcInvocation) msg);
            Object object = registryMap.get(rpcInvocation.getClassName());
            Method method = object.getClass().getDeclaredMethod(rpcInvocation.getMethodName(), rpcInvocation.getParameterTypes());
            Object result = method.invoke(object, rpcInvocation.getValues());
            ctx.write(result);
            ctx.flush();
            ctx.close();
        }
    }
}
