package org.zk.dubbo.rpc.protocol.dubbo;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import org.zk.dubbo.remoting.exchange.code2.ExchangeCodec;

import java.io.IOException;

/**
 * Dubbo编码解码器
 *
 * @author zhangkang
 * @date 2022/5/8 18:15
 */
public class DubboCodec extends ExchangeCodec {

    @Override
    public void encodeData(Hessian2Output hessian2Output, Object message) throws IOException {
        // Dubbo中于此不同，这里简单处理，直接序列化整个对象
        hessian2Output.writeObject(message);
    }

    @Override
    public Object decodeData(Hessian2Input hessian2Input) throws IOException {
        return hessian2Input.readObject();
    }
}
