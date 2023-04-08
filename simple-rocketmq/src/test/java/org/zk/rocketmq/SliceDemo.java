package org.zk.rocketmq;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author zhangkang
 * @date 2023/4/2 16:37
 */
public class SliceDemo {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        buffer.put("012".getBytes(StandardCharsets.UTF_8));


        ByteBuffer slice = buffer.slice();
        slice.position(0);
        slice.limit(3);


        while (slice.hasRemaining()) {
            System.out.print((char)slice.get());
        }
    }
}
