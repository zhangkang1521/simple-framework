package org.zk.rocketmq;

/**
 * @author zhangkang
 * @date 2023/4/2 22:04
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class LockExceptionDemo {

    private static Lock lock = new ReentrantLock();

    private static Logger log = LoggerFactory.getLogger(LockExceptionDemo.class);


    public static void main(String[] args) throws Exception {
        new Thread(new Task1()).start();
        Thread.sleep(20);
        new Thread(new Task2()).start();

    }

    static class Task1 implements Runnable {

        @Override
        public void run() {
            log.info("task 1 lock");
            lock.lock(); // 获取锁
            lock.lock(); // 获取锁
            try {
                log.info("task1 locked");
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log.info("task1 unlock");
                lock.unlock();
                lock.unlock();
            }
        }
    }

    static class Task2 implements Runnable {

        @Override
        public void run() {
            log.info("task 2 lock");
            lock.lock(); // 获取锁
            try {
                log.info("task2 locked");
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log.info("task2 unlock");
                lock.unlock();
            }
        }
    }
}

