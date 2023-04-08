package org.zk.rocketmq.store;

import org.zk.rocketmq.common.message.Message;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author zhangkang
 * @date 2023/4/8 18:03
 */
public class CommitLog {

    private MappedFile mappedFile;

    public void load() {
        mappedFile = new MappedFile("D:/simple-rocketmq/commitLog");
    }

    public void putMessage(Message message) {

        // 消息总长度 = totalSize(int) + topicLength + topic + bodyLength + body
        int totalSize = 4
                + 1 + message.getTopic().getBytes(StandardCharsets.UTF_8).length
                + 4 + message.getBody().length;

        ByteBuffer byteBuffer = ByteBuffer.allocate(totalSize);

        // totalSize
        byteBuffer.putInt(totalSize);
        // topic
        byteBuffer.put((byte)message.getTopic().length());
        byteBuffer.put(message.getTopic().getBytes(StandardCharsets.UTF_8));
        // body
        byteBuffer.putInt(message.getBody().length);
        byteBuffer.put(message.getBody());

        this.mappedFile.appendMessage(byteBuffer.array());

    }
}
