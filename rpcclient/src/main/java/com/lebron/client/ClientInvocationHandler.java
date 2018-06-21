package com.lebron.client;

import com.lebron.api.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: shenggao
 * date: 2018/6/21
 */
public class ClientInvocationHandler implements InvocationHandler {
    private String host;
    private int port;

    public ClientInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParams(args);
        request.setParamsType(method.getParameterTypes());
        TcpTransport transport = new TcpTransport(host, port);
        return transport.send(request);
    }
}
