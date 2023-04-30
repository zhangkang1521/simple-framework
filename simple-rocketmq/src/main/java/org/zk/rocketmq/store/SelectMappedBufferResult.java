package org.zk.rocketmq.store;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.ByteBuffer;

/**
 * 读取MappedFile返回封装
 * @author zhangkang
 * @date 2023/4/8 22:19
 */
@AllArgsConstructor
@Data
public class SelectMappedBufferResult {

    private long offset;

    private ByteBuffer byteBuffer;

    private int size;
}
