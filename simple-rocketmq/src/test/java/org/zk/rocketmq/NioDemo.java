package org.zk.rocketmq;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author zhangkang
 * @date 2023/3/18 21:43
 */
public class NioDemo {

    @Test
    public void testWrite() throws Exception {
        String data = "0123456789";
        RandomAccessFile file = new RandomAccessFile("E:/test.txt", "rw");
        FileChannel fileChannel = file.getChannel();

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 1024);

        // slice一个新的缓冲区写入
        ByteBuffer writeByteBuffer = mappedByteBuffer.slice();
        writeByteBuffer.put(data.getBytes(StandardCharsets.UTF_8));


//        ByteBuffer byteBufferNew = mappedByteBuffer.slice();
//        byteBufferNew.position(2);
//        byteBufferNew.limit(5);
        int pos = 2;
        ByteBuffer byteBuffer = mappedByteBuffer.slice();
        byteBuffer.position(pos);
        int size = 10 - pos;
        ByteBuffer byteBufferNew = byteBuffer.slice();
        byteBufferNew.limit(size);

        while (byteBufferNew.hasRemaining()) {
            System.out.println((char)byteBufferNew.get());
        }

        // 刷盘
        // mappedByteBuffer.force();

//        file.close();
    }



}
