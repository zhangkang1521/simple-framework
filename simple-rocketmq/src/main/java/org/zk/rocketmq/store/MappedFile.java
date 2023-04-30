package org.zk.rocketmq.store;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 内存映射文件
 * @author zhangkang
 * @date 2023/4/8 17:51
 */
@Slf4j
public class MappedFile {


    private String path;

    /**
     * 文件
     */
    private RandomAccessFile file;

    /**
     * 文件通道
     */
    private FileChannel fileChannel;

    /**
     * 文件内存映射
     */
    private MappedByteBuffer mappedByteBuffer;

    /**
     * 当前写指针
     */
    protected final AtomicInteger wrotePosition = new AtomicInteger(0);

    @SneakyThrows
    public MappedFile(String path) {
        this.path = path;
        file = new RandomAccessFile(path, "rw");
        fileChannel = file.getChannel();
        mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
    }

    /**
     * 写入
     * @param data
     */
    @SneakyThrows
    public void appendMessage(final byte[] data) {
        int currentPos = this.wrotePosition.get();
        this.fileChannel.position(currentPos);
        this.fileChannel.write(ByteBuffer.wrap(data));
        this.wrotePosition.addAndGet(data.length);
        log.info("写入文件 file:{} currentPos:{} writeSize:{}", path, currentPos, data.length);
        // 同步刷盘
        this.mappedByteBuffer.force();
    }

    /**
     * 从指定位置查询写入的字节
     * @param pos
     * @return
     */
    public SelectMappedBufferResult selectMappedBuffer(int pos) {
        if (pos < wrotePosition.get() && pos >= 0) {
            ByteBuffer byteBuffer = this.mappedByteBuffer.slice();
            byteBuffer.position(pos);
            int limit = this.wrotePosition.get() - pos;
            byteBuffer.limit(limit);
            return new SelectMappedBufferResult(pos, byteBuffer, limit);
        }
        return null;
    }
}
