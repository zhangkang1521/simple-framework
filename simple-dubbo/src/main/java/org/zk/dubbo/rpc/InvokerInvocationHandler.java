package org.zk.dubbo.rpc;

import org.zk.dubbo.config.ReferenceConfig;
import org.zk.dubbo.remoting.transport.netty4.NettyClient;
import org.zk.dubbo.remoting.transport.netty4.NettyClientHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 实现Jdk的InvocationHandler接口，用于客户端代理对象发送请求
 */
public class InvokerInvocationHandler implements InvocationHandler {

    private ReferenceConfig referenceConfig;
    private NettyClient nettyClient;
    private NettyClientHandler nettyClientHandler;

    public InvokerInvocationHandler(ReferenceConfig referenceConfig) {
        this.referenceConfig = referenceConfig;
        this.nettyClientHandler = new NettyClientHandler();
        this.nettyClient = new NettyClient(referenceConfig, nettyClientHandler);
    }

    // 代理入口
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }

        RpcInvocation rpcInvocation = new RpcInvocation();
        rpcInvocation.setClassName(referenceConfig.getInterfaceClass().getName());
        rpcInvocation.setMethodName(method.getName());
        rpcInvocation.setParameterTypes(method.getParameterTypes());
        rpcInvocation.setValues(args);


        nettyClient.send(rpcInvocation);

        return nettyClientHandler.getResponse();
    }
}
