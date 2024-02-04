package org.zk.rocketmq.broker.processor;

import io.netty.channel.ChannelHandlerContext;
import org.zk.rocketmq.common.message.Message;
import org.zk.rocketmq.remoting.netty.NettyRequestProcessor;
import org.zk.rocketmq.remoting.protocol.RemotingCommand;
import org.zk.rocketmq.store.DefaultMessageStore;

/**
 * 发送消息处理（broker）
 * @author zhangkang
 * @date 2023/5/3 20:52
 */
public class SendMessageProcessor implements NettyRequestProcessor {

    private DefaultMessageStore defaultMessageStore;

    public SendMessageProcessor(DefaultMessageStore defaultMessageStore) {
        this.defaultMessageStore = defaultMessageStore;
    }

    @Override
    public RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request) {
        String topic = (String)request.getHeader().get("topic");
        Message message = new Message();
        message.setTopic(topic);
        message.setBody(request.getBody());
        defaultMessageStore.putMessage(message);
        return null;
    }
}
