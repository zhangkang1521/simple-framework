package org.zk.rocketmq.broker;

import org.zk.rocketmq.broker.processor.SendMessageProcessor;
import org.zk.rocketmq.common.message.Message;
import org.zk.rocketmq.common.protocol.RequestCode;
import org.zk.rocketmq.remoting.netty.NettyRemotingServer;
import org.zk.rocketmq.store.DefaultMessageStore;

import java.nio.charset.StandardCharsets;

/**
 * Broker启动类
 * @author zhangkang
 * @date 2023/4/8 17:06
 */
public class BrokerStartup {

    public static void main(String[] args) {
        // 消息存储
        DefaultMessageStore defaultMessageStore = new DefaultMessageStore();
        // 启动Netty服务端
        NettyRemotingServer nettyRemotingServer = new NettyRemotingServer();
        // 注册消息发送处理器
        nettyRemotingServer.registerProcessor(RequestCode.SEND_MSG, new SendMessageProcessor(defaultMessageStore));
        nettyRemotingServer.start();



        // TODO 测试
//        Message message = new Message();
//        message.setTopic("test-topic");
//        message.setBody("abc".getBytes(StandardCharsets.UTF_8));
//        defaultMessageStore.putMessage(message);
//
//        Message message2 = new Message();
//        message2.setTopic("test-topic-2");
//        message2.setBody("def".getBytes(StandardCharsets.UTF_8));
//        defaultMessageStore.putMessage(message2);
    }
}
