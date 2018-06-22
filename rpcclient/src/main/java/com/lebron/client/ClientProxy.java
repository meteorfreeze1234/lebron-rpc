package com.lebron.client;

import com.lebron.client.zk.IServiceDiscover;

import java.lang.reflect.Proxy;

/**
 * @author: shenggao
 * date: 2018/6/21
 */
public class ClientProxy {
    public <T> T clientProxy(final Class<T> clazz, IServiceDiscover serviceDiscover) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ClientInvocationHandler(serviceDiscover));
    }
}
