package org.zk.dubbo.rpc.protocol.dubbo;

import org.zk.dubbo.common.URL;
import org.zk.dubbo.remoting.exchange.Request;
import org.zk.dubbo.remoting.transport.netty4.NettyClient;
import org.zk.dubbo.remoting.transport.netty4.NettyClientHandler;
import org.zk.dubbo.rpc.Invoker;
import org.zk.dubbo.rpc.RpcInvocation;

public class DubboInvoker<T> implements Invoker<T> {

    private URL url;
    private NettyClient nettyClient;

    public DubboInvoker(URL url) {
        this.url = url;
        this.nettyClient = new NettyClient(url, new NettyClientHandler());
    }

    @Override
    public Object invoke(RpcInvocation invocation) {
        Request request = new Request();
        request.setRpcInvocation(invocation);
        // dubbo协议使用netty
        return nettyClient.send(request).get();
    }

    @Override
    public URL getUrl() {
        return url;
    }
}
