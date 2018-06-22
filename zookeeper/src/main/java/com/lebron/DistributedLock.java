package com.lebron;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author: shenggao
 * date: 2018/6/22
 */
public class DistributedLock implements Lock, Watcher {
    private ZooKeeper zooKeeper;
    private final String LOCK = "/locks";
    private CountDownLatch countDownLatch;
    private String currentLock;
    private String waitLock;
    public DistributedLock() {
        try {
            zooKeeper = new ZooKeeper("192.168.143.151:2181", 4000, this);
            Stat stat = zooKeeper.exists(LOCK, true);
            if (stat == null) {
                zooKeeper.create(LOCK, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lock() {
        if (tryLock()) {
            System.out.println(Thread.currentThread().getName() + "获得锁成功");
            unlock();
            return;
        }
        waitForLock(waitLock);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }


    private void waitForLock(String prev) {
        try {
            Stat stat = zooKeeper.exists(prev, true);
            if (stat != null) {
                System.out.println(Thread.currentThread().getName() + "等待锁" + prev +"释放");
                countDownLatch = new CountDownLatch(1);
                countDownLatch.await();
                System.out.println(Thread.currentThread().getName() + "获得锁");
                unlock();
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean tryLock() {
        try {
            currentLock = zooKeeper.create(LOCK + "/", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName()+"->"+
                    currentLock+"，尝试竞争锁");
            List<String> children = zooKeeper.getChildren(LOCK, false);
            final SortedSet<String> set = new TreeSet<>(children);
            children.forEach(item -> set.add(LOCK + "/" +item));
            String first = set.first();
            if (currentLock.equals(first)) {
                return true;
            }
            SortedSet<String> lessThenMe = set.headSet(currentLock);
            if (!lessThenMe.isEmpty()) {
                waitLock = lessThenMe.last();
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        System.out.println(Thread.currentThread().getName()+"->释放锁"+currentLock);
        try {
            zooKeeper.delete(currentLock, -1);
            currentLock = null;
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void process(WatchedEvent event) {
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }
}
