package org.zk.dubbo.rpc.protocol.dubbo;

import org.zk.dubbo.common.URL;
import org.zk.dubbo.remoting.exchange.Request;
import org.zk.dubbo.remoting.transport.netty4.NettyClient;
import org.zk.dubbo.remoting.transport.netty4.NettyClientHandler;
import org.zk.dubbo.rpc.Invoker;
import org.zk.dubbo.rpc.RpcInvocation;

public class DubboInvoker<T> implements Invoker<T> {

    private Class<T> type;
    private URL url;
    private NettyClient nettyClient;

    public DubboInvoker(Class<T> type, URL url) {
        this.type = type;
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
    public Class<T> getInterface() {
        return type;
    }

    @Override
    public URL getUrl() {
        return url;
    }
}
