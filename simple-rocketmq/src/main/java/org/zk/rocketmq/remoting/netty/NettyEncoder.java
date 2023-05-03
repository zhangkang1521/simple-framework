package org.zk.rocketmq.remoting.netty;

import cn.hutool.core.util.SerializeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.zk.rocketmq.remoting.protocol.RemotingCommand;

import java.util.List;

/**
 * 出站-编码
 * @author zhangkang
 * @date 2023/5/3 19:46
 */
public class NettyEncoder extends MessageToMessageEncoder<RemotingCommand> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RemotingCommand remotingCommand, List<Object> out) throws Exception {
        // 将消息对象序列化为字节数组
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeBytes(SerializeUtil.serialize(remotingCommand));

        // 添加到out输出列表,传递给下一个Handler发送
        out.add(buf);
    }
}
