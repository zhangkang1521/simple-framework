package org.zk.rocketmq.store;

import lombok.extern.slf4j.Slf4j;
import org.zk.rocketmq.common.message.Message;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author zhangkang
 * @date 2023/3/26 20:44
 */
@Slf4j
public class DefaultMessageStore {

    private CommitLog commitLog;

    private ReputMessageService reputMessageService;



    public DefaultMessageStore() {
        this.commitLog = new CommitLog();
        this.commitLog.load();

        log.info("reputMessageService started!");
        this.reputMessageService = new ReputMessageService();
        new Thread(this.reputMessageService).start();
    }

    public void putMessage(Message message) {
       this.commitLog.putMessage(message);
    }

    /**
     * 消息转发
     */
    class ReputMessageService implements Runnable {

        @Override
        public void run() {

        }
    }
}
