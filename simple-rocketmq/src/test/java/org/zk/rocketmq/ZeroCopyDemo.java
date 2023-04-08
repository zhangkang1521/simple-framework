package org.zk.rocketmq;

/**
 * @author zhangkang
 * @date 2023/3/25 22:17
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ZeroCopyDemo {

    /**
     * 我们使用了 Java NIO 库中的 MappedByteBuffer 类，
     * 将输入文件中的数据映射到内存中。
     * 然后，我们直接将映射的 ByteBuffer 对象传递给输出文件的通道，
     * 从而避免了数据的复制。
     * 这样，我们就实现了 Java 中的 0 拷贝技术，提高了 IO 操作的效率
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        long start = System.currentTimeMillis();
        // 打开读取文件的通道
        FileInputStream inputStream = new FileInputStream("E:/source_file.dat");
        FileChannel inputChannel = inputStream.getChannel();

        // 打开写入文件的通道
        FileOutputStream outputStream = new FileOutputStream("E:/destination_file_zero_copy.dat");
        FileChannel outputChannel = outputStream.getChannel();

        // 获取读取文件的大小
        long size = inputChannel.size();

        // 使用内存映射文件创建一个 MappedByteBuffer 对象
        MappedByteBuffer mappedByteBuffer = inputChannel.map(FileChannel.MapMode.READ_ONLY, 0, size);

        // 将 MappedByteBuffer 中的数据直接复制到输出通道中
        outputChannel.write(mappedByteBuffer);

        // 关闭所有通道和流
        inputChannel.close();
        inputStream.close();
        outputChannel.close();
        outputStream.close();

        System.out.println(System.currentTimeMillis() - start);
        // 0拷贝： 2000ms  传统拷贝： 4000ms
    }
}

