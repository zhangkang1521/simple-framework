package org.zk.dubbo.rpc;

import org.zk.dubbo.config.ReferenceConfig;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 实现Jdk的InvocationHandler接口，用于客户端代理对象发送请求
 */
public class InvokerInvocationHandler implements InvocationHandler {


    private Invoker<?> invoker;

    private ReferenceConfig<?> referenceConfig;

    public InvokerInvocationHandler(Invoker<?> invoker, ReferenceConfig<?> referenceConfig) {
       this.invoker = invoker;
       this.referenceConfig = referenceConfig;
    }

    // 代理入口
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }
        // 客户端发起调用
        RpcInvocation rpcInvocation = new RpcInvocation();
        rpcInvocation.setClassName(referenceConfig.getInterfaceClass().getName());
        rpcInvocation.setMethodName(method.getName());
        rpcInvocation.setParameterTypes(method.getParameterTypes());
        rpcInvocation.setValues(args);
        return invoker.invoke(rpcInvocation);
    }
}
