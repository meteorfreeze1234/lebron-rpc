package com.lebron;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Hello world!
 */
public class Application {
    public static void main(String[] args) {
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
    }
}
