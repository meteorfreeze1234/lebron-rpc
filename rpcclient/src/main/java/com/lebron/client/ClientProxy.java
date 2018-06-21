package com.lebron.client;

import java.lang.reflect.Proxy;

/**
 * @author: shenggao
 * date: 2018/6/21
 */
public class ClientProxy {
    public <T> T clientProxy(final Class<T> clazz, final String host, final int port) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ClientInvocationHandler(host, port));
    }
}
