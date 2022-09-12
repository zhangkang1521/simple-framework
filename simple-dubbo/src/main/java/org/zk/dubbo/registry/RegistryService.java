package org.zk.dubbo.registry;


import org.zk.dubbo.common.URL;

/**
 * 注册中心服务接口
 *
 * @author zhangkang
 * @date 2022/5/15 16:33
 */
public interface RegistryService {

    /**
     * 注册服务到注册中心
     * @param url
     */
    void register(URL url);
}
