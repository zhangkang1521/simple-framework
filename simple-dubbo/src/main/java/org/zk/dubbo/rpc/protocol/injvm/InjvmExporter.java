package org.zk.dubbo.rpc.protocol.injvm;

import org.zk.dubbo.rpc.Exporter;
import org.zk.dubbo.rpc.Invoker;

public class InjvmExporter<T> implements Exporter {

    private final Invoker<T> invoker;

    public InjvmExporter(Invoker<T> invoker) {
        this.invoker = invoker;
    }

    @Override
    public Invoker<T> getInvoker() {
        return invoker;
    }
}
