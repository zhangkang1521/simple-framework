package org.zk.rocketmq.store;

import java.nio.ByteBuffer;

/**
 * @author zhangkang
 * @date 2023/4/30 20:20
 */
public class ConsumeQueue {

    /**
     * 每个单元大小
     */
    private static final int CQ_STORE_UNIT_SIZE = 8 + 4;

    /**
     * 对应文件
     */
    private MappedFile mappedFile;

    private final ByteBuffer byteBufferIndex;

    public ConsumeQueue(String topic) {
        mappedFile = new MappedFile("D:/simple-rocketmq/consumeQueue/" + topic);
        this.byteBufferIndex = ByteBuffer.allocate(CQ_STORE_UNIT_SIZE);
    }

    public void putMessagePositionInfoWrapper(DispatchRequest request) {
        byteBufferIndex.position(0);
        byteBufferIndex.limit(CQ_STORE_UNIT_SIZE);
        byteBufferIndex.putLong(request.getCommitLogOffset());
        byteBufferIndex.putInt(request.getMsgSize());

        mappedFile.appendMessage(byteBufferIndex.array());
    }
}
