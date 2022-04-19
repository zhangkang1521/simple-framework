package org.zk.dubbo.remoting.exchange.support;

import lombok.extern.slf4j.Slf4j;
import org.zk.dubbo.remoting.exchange.Response;
import org.zk.dubbo.remoting.exchange.ResponseFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 请求线程阻塞，结果返回唤起线程处理
 */
@Slf4j
public class DefaultFuture implements ResponseFuture {

    /**
     * 请求id
     */
    private Long requestId;

    /**
     * 返回结果
     */
    private Object response;

    /**
     * 锁
     */
    private Lock lock = new ReentrantLock();

    /**
     * 结果是否返回
     */
    private Condition done = lock.newCondition();

    /**
     * requestId -> DefaultFuture
     */
    private static final Map<Long, DefaultFuture> FUTURE_MAP = new ConcurrentHashMap<>();

    /**
     * 发起请求调用
     * @param requestId
     */
    public DefaultFuture(Long requestId) {
        this.requestId = requestId;
        FUTURE_MAP.put(requestId, this);
    }


    @Override
    public Object get() {
        try {
            lock.lock();
            log.info("requestId:{} 阻塞，等待结果返回", requestId);
            done.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return response;
    }

    /**
     * 结果收到调用
     * @param response
     */
    public static void receive(Response response) {
        DefaultFuture defaultFuture = FUTURE_MAP.remove(response.getMId());
        if (defaultFuture != null) {
            defaultFuture.doReceive(response);
        }
    }

    private void doReceive(Response response) {
        try {
            lock.lock();
            this.response = response.getMResult();
            log.info("requestId:{} 结果已返回，唤醒阻塞线程", requestId);
            done.signal();
        } finally {
            lock.unlock();
        }
    }
}
