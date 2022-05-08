package org.zk.dubbo.remoting.exchange.code2;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import io.netty.buffer.ByteBuf;
import org.zk.dubbo.remoting.transport.AbstractCodec;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * ExchangeCodec
 * 对Request,Response进行编码解码
 *
 * @author zhangkang
 * @date 2022/5/8 18:14
 */
public class ExchangeCodec extends AbstractCodec {

    private static final int HEADER_LENGTH = 4;

    @Override
    public void encode(ByteBuf bytebuf, Object message) throws IOException {
        Hessian2Output hessian2Output = getSerialization(bytebuf);
        int startIdx = bytebuf.writerIndex();

        bytebuf.writerIndex(startIdx + HEADER_LENGTH);

        // DubboCode2覆盖此方法
        encodeData(hessian2Output, message);

        hessian2Output.flush();
        hessian2Output.close();

        int endIdx = bytebuf.writerIndex();

        // 写入消息头
        bytebuf.setInt(startIdx, endIdx - startIdx - HEADER_LENGTH);

        // 指针回归到尾部
        bytebuf.writerIndex(endIdx);
    }

    protected void encodeData(Hessian2Output hessian2Output, Object message) throws IOException {
        // 子类实现
    }

    @Override
    public Object decode(ByteBuf byteBuf) throws IOException {
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Hessian2Input hessian2Input = new Hessian2Input(new ByteArrayInputStream(bytes));

        // DubboCode2 覆盖此方法
        Object obj = decodeData(hessian2Input);

        hessian2Input.close();

        return obj;
    }

    protected Object decodeData(Hessian2Input hessian2Input) throws IOException {
        // 子类实现
        return null;
    }
}
