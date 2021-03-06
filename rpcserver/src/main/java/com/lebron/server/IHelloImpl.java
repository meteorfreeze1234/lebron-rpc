package com.lebron.server;

import com.lebron.api.IHello;
import com.lebron.server.zk.IRegistryCenter;
import com.lebron.server.zk.IRegistyCenterImpl;

/**
 * @author: shenggao
 * date: 2018/6/21
 */
public class IHelloImpl implements IHello {
    @Override
    public String hello() {
        return "Hello sg from 8000";
    }


    public static void main(String[] args) {
        IRegistryCenter registryCenter = new IRegistyCenterImpl();
        Server server = new Server(registryCenter, 8000);
        server.registryService(new IHelloImpl(), IHello.class);
        server.publish();
    }
}
