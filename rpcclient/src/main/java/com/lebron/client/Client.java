package com.lebron.client;

import com.lebron.api.IHello;

/**
 * @author: shenggao
 * date: 2018/6/21
 */
public class Client {
    public static void main(String[] args) {
        IHello hello = new ClientProxy().clientProxy(IHello.class, "127.0.0.1", 8000);
        System.out.print(hello.hello());
    }
}
