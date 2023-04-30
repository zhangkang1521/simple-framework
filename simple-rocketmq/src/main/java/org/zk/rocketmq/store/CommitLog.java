package org.zk.rocketmq.store;

import lombok.extern.slf4j.Slf4j;
import org.zk.rocketmq.common.message.Message;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangkang
 * @date 2023/4/8 18:03
 */
@Slf4j
public class CommitLog {

    /**
     * 对应文件
     */
    private MappedFile mappedFile;

    /**
     * ConsumeQueue文件位移记录
     * topic(简化只有1个队列) -> offset
     */
    private Map<String, Long> topicQueueTable = new HashMap<>();

    public void load() {
        mappedFile = new MappedFile("D:/simple-rocketmq/commitLog");
    }

    public void putMessage(Message message) {

        // 消息总长度 = totalSize(int) + topicQueueOffset + topicLength + topic + bodyLength + body
        int totalSize = 4
                + 8
                + 8
                + 1 + message.getTopic().getBytes(StandardCharsets.UTF_8).length
                + 4 + message.getBody().length;

        ByteBuffer byteBuffer = ByteBuffer.allocate(totalSize);

        // totalSize
        byteBuffer.putInt(totalSize);

        // 消息队列位移
        Long queueOffset = topicQueueTable.computeIfAbsent(message.getTopic(), v -> 0L);
        byteBuffer.putLong(queueOffset);
        topicQueueTable.put(message.getTopic(), ++queueOffset);

        // 消息偏移量
        int currentCommitLogPos = this.mappedFile.wrotePosition.get();
        byteBuffer.putLong(currentCommitLogPos);

        // topic
        byteBuffer.put((byte)message.getTopic().length());
        byteBuffer.put(message.getTopic().getBytes(StandardCharsets.UTF_8));

        // body
        byteBuffer.putInt(message.getBody().length);
        byteBuffer.put(message.getBody());



        this.mappedFile.appendMessage(byteBuffer.array());

        log.info("写入消息成功 curPos:{} totalSize:{} topic:{} body:{}", currentCommitLogPos, totalSize, message.getTopic(), message.getBody());
    }

    /**
     * 读取数据到ByteBuffer
     * @param offset
     * @return
     */
    public SelectMappedBufferResult getData(int offset) {
        return mappedFile.selectMappedBuffer(offset);
    }

    /**
     * 读取消息，用于转发
     * @param byteBuffer
     * @return
     */
    public DispatchRequest checkMessageAndReturnSize(ByteBuffer byteBuffer) {

        // totalSize
        int totalSize = byteBuffer.getInt();

        // queueOffset
        long queueOffset = byteBuffer.getLong();

        // commitLogOffset
        long commitLogOffset = byteBuffer.getLong();

        // topic
        byte topicLen = byteBuffer.get();
        byte[] topicBytes = new byte[topicLen];
        byteBuffer.get(topicBytes, 0, topicLen);
        String topic = new String(topicBytes);

        // body
        int bodyLen = byteBuffer.getInt();
        byte[] bodyBytes = new byte[bodyLen];
        byteBuffer.get(bodyBytes, 0, bodyLen);

        return new DispatchRequest(commitLogOffset, totalSize, topic);
    }
}
