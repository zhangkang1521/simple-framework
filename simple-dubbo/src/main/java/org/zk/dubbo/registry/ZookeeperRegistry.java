package org.zk.dubbo.registry;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.zk.dubbo.common.URL;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Zookeeper注册中心
 *
 * @author zhangkang
 * @date 2022/5/15 18:00
 */
@Slf4j
public class ZookeeperRegistry implements Registry {

    private CuratorFramework curator;

    public ZookeeperRegistry() {
        // TODO 地址获取
        curator = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .retryPolicy(new RetryNTimes(1, 1000))
                .connectionTimeoutMs(5000)
                .build();
        curator.start();
        log.info("curator start");

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                log.info("curator close");
                curator.close();
            }
        });
    }

    @Override
    public URL getUrl() {
        return null;
    }

    @Override
    public void register(URL url) {
        String path = toCategoryPath(url);
        createPath(path, true);
    }

    private void createPath(String path, boolean ephemeral) {
        int index = path.lastIndexOf("/");
        if (index != 0) {
            // 递归创建上级目录
            String parentPath = path.substring(0, index);
            createPath(parentPath, false);
        }
        // 创建本目录
        doCreatePath(path, ephemeral);
    }

    private void doCreatePath(String path, boolean ephemeral) {
        if (ephemeral) {
            createEphemeralNode(path);
        } else {
            createPersistentNode(path);
        }
    }

    private void createPersistentNode(String path) {
        try {
            Stat stat = curator.checkExists().forPath(path);
            if (stat != null) {
                log.info("节点{}已存在无需创建", path);
                return;
            }
            curator.create().forPath(path);
            log.info("创建zookeeper永久节点 {}", path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createEphemeralNode(String path) {
        try {
            Stat stat = curator.checkExists().forPath(path);
            if (stat != null) {
                return;
            }
            // ephemeral
            log.info("创建zookeeper临时节点 {}", path);
            curator.create().withMode(CreateMode.EPHEMERAL).forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String toCategoryPath(URL url) {
        try {
            return "/simple-dubbo/" + url.getPath() + "/providers/" + URLEncoder.encode(url.toFullString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
