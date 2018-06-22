package com.lebron.server.zk;

/**
 * @author: shenggao
 * date: 2018/6/22
 */
public interface IRegistryCenter {
    void registry(String serviceName, String serviceAddress);
}
