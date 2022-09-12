package org.zk.dubbo.config;

import lombok.Data;

/**
 * 接口配置
 *
 * @author zhangkang
 * @date 2022/5/15 15:57
 */
@Data
public abstract class AbstractInterfaceConfig {

    /**
     * 注册中心
     */
    protected RegistryConfig registry;
}
