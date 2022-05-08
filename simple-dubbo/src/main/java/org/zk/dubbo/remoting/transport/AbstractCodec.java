package org.zk.dubbo.remoting.transport;

import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import org.zk.dubbo.remoting.Codec2;

/**
 * 抽象编码解码器
 *
 * @author zhangkang
 * @date 2022/5/8 18:11
 */
public abstract class AbstractCodec implements Codec2 {

    protected Hessian2Output getSerialization(ByteBuf out) {
        // 考虑实现不同序列化策略
        ByteBufOutputStream bout = new ByteBufOutputStream(out);
        return new Hessian2Output(bout);
    }
}
