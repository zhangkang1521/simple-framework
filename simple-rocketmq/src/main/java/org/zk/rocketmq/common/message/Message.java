package org.zk.rocketmq.common.message;

import java.io.Serializable;

/**
 * 消息
 * @author zhangkang
 * @date 2023/2/26 20:49
 */
public class Message implements Serializable {

    /**
     * 主体
     */
    private String topic;

    /**
     * 消息体
     */
    private byte[] body;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
