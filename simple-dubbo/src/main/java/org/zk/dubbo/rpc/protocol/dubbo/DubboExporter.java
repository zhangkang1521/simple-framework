package org.zk.dubbo.rpc.protocol.dubbo;

import org.zk.dubbo.rpc.Exporter;
import org.zk.dubbo.rpc.Invoker;

public class DubboExporter<T> implements Exporter {

    private final Invoker<T> invoker;

    public DubboExporter(Invoker<T> invoker) {
        this.invoker = invoker;
    }

    @Override
    public Invoker<T> getInvoker() {
        return invoker;
    }
}
