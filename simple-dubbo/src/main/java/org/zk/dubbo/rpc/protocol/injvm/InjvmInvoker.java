package org.zk.dubbo.rpc.protocol.injvm;

import org.zk.dubbo.common.URL;
import org.zk.dubbo.rpc.Exporter;
import org.zk.dubbo.rpc.Invoker;
import org.zk.dubbo.rpc.RpcInvocation;

import java.util.Map;

/**
 * 客户端，服务端在同一虚拟机调用
 * @param <T>
 */
public class InjvmInvoker<T> implements Invoker {

    private Class<T> type;
    private URL url;

    private final Map<String, Exporter<?>> exporterMap;

    public InjvmInvoker(Class<T> type, URL url, Map<String, Exporter<?>> exporterMap) {
        this.type = type;
        this.url = url;
        this.exporterMap = exporterMap;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public Object invoke(RpcInvocation invocation) {
        // 直接对内存中的invoker进行调用
        Invoker<?> invoker = exporterMap.get(invocation.getClassName()).getInvoker();
        return invoker.invoke(invocation);
    }

    @Override
    public Class getInterface() {
        return type;
    }

}
