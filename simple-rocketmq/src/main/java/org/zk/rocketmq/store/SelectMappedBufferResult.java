package org.zk.rocketmq.store;

import java.nio.ByteBuffer;

/**
 * @author zhangkang
 * @date 2023/4/8 22:19
 */
public class SelectMappedBufferResult {

    private long offset;

    private ByteBuffer byteBuffer;

    private int size;
}
