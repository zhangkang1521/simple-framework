package org.zk.dubbo.remoting.exchange;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果
 */
@Data
public class Response implements Serializable {

    /**
     * 对应请求的id
     */
    private long mId;

    /**
     * 返回结果
     */
    private Object mResult;
}
