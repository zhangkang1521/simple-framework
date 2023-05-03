package org.zk.rocketmq.store;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.zk.rocketmq.common.message.Message;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangkang
 * @date 2023/3/26 20:44
 */
@Slf4j
public class DefaultMessageStore {

    private CommitLog commitLog;

    private ReputMessageService reputMessageService;

    /**
     * 存储topic -> ConsumeQueue
     */
    private ConcurrentMap<String, ConsumeQueue> consumeQueueTable;


    public DefaultMessageStore() {
        this.commitLog = new CommitLog();
        this.commitLog.load();

        this.consumeQueueTable = new ConcurrentHashMap<>();

        log.info("reputMessageService started!");
        this.reputMessageService = new ReputMessageService();
        // TODO zk
        // new Thread(this.reputMessageService).start();
    }

    public void putMessage(Message message) {
        this.commitLog.putMessage(message);
    }

    /**
     * 消息转发
     */
    class ReputMessageService implements Runnable {

        private int reputOffset = 0;

        @Override
        @SneakyThrows
        public void run() {
            while (true) {
                TimeUnit.SECONDS.sleep(5);

                SelectMappedBufferResult selectMappedBufferResult = DefaultMessageStore.this.commitLog.getData(reputOffset);
                log.info("从{}位置开始读取commitLog 返回：{}", reputOffset, selectMappedBufferResult);
                if (selectMappedBufferResult != null) {
                    int readSize = 0;
                    do {
                        DispatchRequest dispatchRequest = DefaultMessageStore.this.commitLog.checkMessageAndReturnSize(selectMappedBufferResult.getByteBuffer());
                        log.info("解析消息：{}", dispatchRequest);
                        // 找到对应的ConsumeQueue
                        ConsumeQueue consumeQueue = DefaultMessageStore.this.findConsumeQueue(dispatchRequest.getTopic());
                        // 写入consumeQueue
                        consumeQueue.putMessagePositionInfoWrapper(dispatchRequest);
                        readSize += dispatchRequest.getMsgSize();
                    } while (readSize < selectMappedBufferResult.getSize());

                    reputOffset += selectMappedBufferResult.getSize();
                }
            }
        }
    }

    public ConsumeQueue findConsumeQueue(String topic) {
        return this.consumeQueueTable.computeIfAbsent(topic, c -> new ConsumeQueue(topic));
    }
}
