package org.zk.dubbo;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * CuratorFramework api 测试
 *
 * @author zhangkang
 * @date 2022/5/8 20:38
 */
@Slf4j
public class CuratorTest {

    private CuratorFramework curator;

    @Before
    public void before() {
        //通过工厂创建Curator
        curator = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .retryPolicy(new RetryNTimes(1, 1000))
                .build();
        curator.start();
    }

    @After
    public void after() {
        curator.close();
    }

    @Test
    public void createPersist() throws Exception {
        // 创建节点，默认永久节点
        // create /test ip
        curator.create().forPath("/test");
    }

    @Test
    public void createEphemeral() throws Exception {
        // 创建临时节点
        // create -e /temp xxx
        curator.create().withMode(CreateMode.EPHEMERAL).forPath("/temp");
    }

    @Test
    public void checkExist() throws Exception {
        // 有返回值则存在
        Stat stat = curator.checkExists().forPath("/test");
        Stat stat2 = curator.checkExists().forPath("/test2");
        System.out.println(stat);
    }

    @Test
    public void getChildren() throws Exception {
        List<String> children = curator.getChildren().forPath("/test");
        System.out.println(children);
    }

    @Test
    public void delete() throws Exception {
        // 如果还存在子目录会报错
        curator.delete().forPath("/test/1");
    }

    @Test
    public void watch() throws Exception {
        curator.getChildren().usingWatcher(new CuratorWatcher() {
            @Override
            public void process(WatchedEvent watchedEvent) throws Exception {
                log.info("节点发生变化 {}", watchedEvent.getPath());

                // 不加这行，只能监听到一次
                List<String> children = curator.getChildren().usingWatcher(this).forPath("/test");
                log.info("当前子节点: {}", children);
            }
        }).forPath("/test");
        System.in.read();
    }
}
