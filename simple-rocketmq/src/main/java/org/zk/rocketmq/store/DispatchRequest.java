package org.zk.rocketmq.store;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhangkang
 * @date 2023/4/8 22:30
 */
@Data
@AllArgsConstructor
public class DispatchRequest {

    /**
     * 消息offset
     */
    private long commitLogOffset;

    /**
     * 消息大小
     */
    private int msgSize;

    /**
     * 消息主题（用于确定哪个文件目录）
     */
    private String topic;
}
