package org.zk.dubbo.config;

import lombok.Data;

/**
 * 注册中心配置
 *
 * @author zhangkang
 * @date 2022/5/15 15:51
 */
@Data
public class RegistryConfig {

    /**
     * 注册中心地址，例如 zookeeper://localhost:2181
     */
    private String address;

    public RegistryConfig(String address) {
        this.address = address;
    }
}
