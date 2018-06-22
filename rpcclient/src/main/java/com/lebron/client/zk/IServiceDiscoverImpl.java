package com.lebron.client.zk;


import com.lebron.client.lb.ILoadBalance;
import com.lebron.client.lb.RandomLoadBalance;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

/**
 * @author: shenggao
 * date: 2018/6/22
 */
public class IServiceDiscoverImpl implements IServiceDiscover {
    private CuratorFramework curatorFramework;
    private List<String> repos;
    private ILoadBalance loadBalance;

    public IServiceDiscoverImpl() {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(ZkConfig.ADDRESS_STRING)
                .sessionTimeoutMs(4000).namespace(ZkConfig.REGISTRY)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        curatorFramework.start();
        loadBalance = new RandomLoadBalance();
    }

    @Override
    public String discover(String serviceName) {
        String servicePath = "/" + serviceName;
        try {
            repos = curatorFramework.getChildren().forPath(servicePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        registryWatcher(servicePath);
        return loadBalance.selectHost(repos);
    }

    private void registryWatcher(final String path) {
        PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework, path, true);
        childrenCache.getListenable().addListener((curatorFramework, pathChildrenCacheEvent) -> repos = curatorFramework.getChildren().forPath(path));
        try {
            childrenCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
