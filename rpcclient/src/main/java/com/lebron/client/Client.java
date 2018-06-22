package com.lebron.client;

import com.lebron.api.IHello;
import com.lebron.client.zk.IServiceDiscover;
import com.lebron.client.zk.IServiceDiscoverImpl;

/**
 * @author: shenggao
 * date: 2018/6/21
 */
public class Client {
    public static void main(String[] args) {
        IServiceDiscover iServiceDiscover = new IServiceDiscoverImpl();
        IHello hello = new ClientProxy().clientProxy(IHello.class, iServiceDiscover);
        for (int i = 0; i < 10; i++) {
            System.out.println(hello.hello());
        }
    }
}
