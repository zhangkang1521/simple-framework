package org.zk.rocketmq.remoting.netty;

import cn.hutool.core.util.SerializeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.zk.rocketmq.remoting.protocol.RemotingCommand;

import java.util.List;

/**
 * 入站-解码
 * @author zhangkang
 * @date 2023/5/3 19:49
 */
public class NettyDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {

        int length = byteBuf.readableBytes();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        RemotingCommand remotingCommand = SerializeUtil.deserialize(bytes);

        list.add(remotingCommand);
    }
}
