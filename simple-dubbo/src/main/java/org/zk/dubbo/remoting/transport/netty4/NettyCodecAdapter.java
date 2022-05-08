package org.zk.dubbo.remoting.transport.netty4;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Netty编码解码适配器
 *
 * @author zhangkang
 * @date 2022/5/2 21:20
 */
@Getter
final class NettyCodecAdapter {

    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    private final ChannelHandler encoder = new InternalEncoder();

    private final ChannelHandler decoder = new InternalDecoder();

//    private final Codec2 codec;


    private class InternalEncoder extends MessageToByteEncoder {


        @Override
        protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
            // msg可能是Request/Response
            int startIdx = out.writerIndex();
            ByteBufOutputStream bout = new ByteBufOutputStream(out);
            bout.write(LENGTH_PLACEHOLDER);
            //
            Hessian2Output oout = new Hessian2Output(bout);
            oout.writeObject(msg);
            oout.flush();
            oout.close();

            int endIdx = out.writerIndex();
            out.setInt(startIdx, endIdx - startIdx - 4);
        }
    }

    private class InternalDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            int length = in.readInt();
            byte[] bytes = new byte[length];
            in.readBytes(bytes);

            Hessian2Input hessian2Input = new Hessian2Input(new ByteArrayInputStream(bytes));
            Object obj =  hessian2Input.readObject();
            hessian2Input.close();
            out.add(obj);
        }
    }
}
