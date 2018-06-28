package com.lebron;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Hello world!
 */
public class Application {
    public static void main(String[] args) throws IOException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    DistributedLock distributedLock = new DistributedLock();
                    distributedLock.lock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            countDownLatch.countDown();
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new ZooKeeper("192.168.143.151:2181", 4000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("event = [" + event + "]");
            }
        });
    }
}
