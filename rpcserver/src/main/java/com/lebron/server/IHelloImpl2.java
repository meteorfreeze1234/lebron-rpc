package com.lebron.server;

import com.lebron.api.IHello;
import com.lebron.server.zk.IRegistryCenter;
import com.lebron.server.zk.IRegistyCenterImpl;

/**
 * @author: shenggao
 * date: 2018/6/21
 */
public class IHelloImpl2 implements IHello {
    @Override
    public String hello() {
        return "Hello sg from 8001";
    }


    public static void main(String[] args) {
        IRegistryCenter registryCenter = new IRegistyCenterImpl();
        Server server = new Server(registryCenter, 8001);
        server.registryService(new IHelloImpl2(), IHello.class);
        server.publish();
    }
}
