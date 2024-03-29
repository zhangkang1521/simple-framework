package org.zk.rocketmq.client.producer;

import org.zk.rocketmq.common.message.Message;
import org.zk.rocketmq.common.protocol.RequestCode;
import org.zk.rocketmq.remoting.netty.NettyRemotingClient;
import org.zk.rocketmq.remoting.protocol.RemotingCommand;

/**
 * @author zhangkang
 * @date 2023/5/2 22:38
 */
public class DefaultMQProducer {

    private NettyRemotingClient nettyRemotingClient;

    public void start() {
        nettyRemotingClient = new NettyRemotingClient();
        nettyRemotingClient.start();
    }

    public void send(Message message) {
        RemotingCommand remotingCommand = new RemotingCommand();
        remotingCommand.setCode(RequestCode.SEND_MSG);
        remotingCommand.getHeader().put("topic", message.getTopic());
        remotingCommand.setBody(message.getBody());
        nettyRemotingClient.invokeSync(remotingCommand);
    }

}
