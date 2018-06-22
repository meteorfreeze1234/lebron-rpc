package com.lebron.server.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author: shenggao
 * date: 2018/6/22
 */
public class IRegistyCenterImpl implements IRegistryCenter {
    private CuratorFramework curatorFramework;
    public IRegistyCenterImpl() {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(ZkConfig.ADDRESS_STRING)
                .sessionTimeoutMs(4000).namespace(ZkConfig.REGISTRY)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        curatorFramework.start();
    }

    @Override
    public void registry(String serviceName, String serviceAddress) {
        String servicePath = "/" + serviceName;
        try {
            Stat stat = curatorFramework.checkExists().forPath(servicePath);
            if (stat == null) {
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(servicePath, "0".getBytes());
            }
            String addressPath = servicePath + "/" +serviceAddress;
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(addressPath, "0".getBytes());
            System.out.println("服务注册成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
