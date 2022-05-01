package org.zk.dubbo.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 实现Jdk的InvocationHandler接口，用于客户端代理对象发送请求
 */
public class InvokerInvocationHandler implements InvocationHandler {


    private Invoker<?> invoker;

    public InvokerInvocationHandler(Invoker<?> invoker) {
       this.invoker = invoker;
    }

    // 代理入口
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }
        // 客户端发起调用
        RpcInvocation rpcInvocation = new RpcInvocation();
        rpcInvocation.setClassName(invoker.getUrl().getPath());
        rpcInvocation.setMethodName(method.getName());
        rpcInvocation.setParameterTypes(method.getParameterTypes());
        rpcInvocation.setValues(args);
        return invoker.invoke(rpcInvocation);
    }
}
