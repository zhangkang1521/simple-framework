package org.zk.dubbo.remoting.transport.netty4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.Getter;
import org.zk.dubbo.remoting.Codec2;
import org.zk.dubbo.rpc.protocol.dubbo.DubboCodec;

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

    private final Codec2 codec = new DubboCodec();



    private class InternalEncoder extends MessageToByteEncoder {


        @Override
        protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
            codec.encode(out, msg);
        }
    }

    private class InternalDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
           Object obj = codec.decode(in);
           out.add(obj);
        }
    }
}
