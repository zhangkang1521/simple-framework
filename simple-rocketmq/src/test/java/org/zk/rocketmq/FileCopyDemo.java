package org.zk.rocketmq;

/**
 * @author zhangkang
 * @date 2023/3/25 22:15
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopyDemo {
    public static void main(String[] args) throws IOException {

        long start = System.currentTimeMillis();

        // 输入文件和输出文件路径
        String srcPath = "E:/source_file.dat";
        String destPath = "E:/destination_file.dat";

        // 创建输入文件流和输出文件流
        FileInputStream fis = new FileInputStream(srcPath);
        FileOutputStream fos = new FileOutputStream(destPath);

        // 缓冲区大小
        byte[] buffer = new byte[1024];

        // 读取输入文件流，并写入输出文件流
        int len;
        while ((len = fis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }

        // 关闭输入文件流和输出文件流
        fis.close();
        fos.close();

        System.out.println(System.currentTimeMillis() - start);
    }
}

