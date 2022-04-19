package org.zk.dubbo.rpc;

import lombok.Data;

import java.io.Serializable;

/**
 * Rpc调用所需字段组合
 */
@Data
public class RpcInvocation implements Serializable {

    /**
     * 完整类名
     */
    private String className;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数类型列表
     */
    private Class<?>[] parameterTypes;

    /**
     * 参数
     */
    private Object[] values;

}
