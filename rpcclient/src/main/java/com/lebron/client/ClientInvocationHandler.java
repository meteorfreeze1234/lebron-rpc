package com.lebron.client;

import com.lebron.api.RpcRequest;
import com.lebron.client.zk.IServiceDiscover;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: shenggao
 * date: 2018/6/21
 */
public class ClientInvocationHandler implements InvocationHandler {
    private IServiceDiscover serviceDiscover;

    public ClientInvocationHandler(IServiceDiscover serviceDiscover) {
        this.serviceDiscover = serviceDiscover;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParams(args);
        request.setParamsType(method.getParameterTypes());
        String address = serviceDiscover.discover(request.getClassName());
        if (address == null) {
            throw new NullPointerException("获取服务失败");
        }
        String[] ipAndPort = address.split(":");
        TcpTransport transport = new TcpTransport(ipAndPort[0], Integer.valueOf(ipAndPort[1]));
        return transport.send(request);
    }
}
