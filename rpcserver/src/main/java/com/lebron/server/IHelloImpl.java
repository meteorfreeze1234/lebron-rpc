package com.lebron.server;

import com.lebron.api.IHello;

/**
 * @author: shenggao
 * date: 2018/6/21
 */
public class IHelloImpl implements IHello {
    @Override
    public String hello() {
        return "Hello sg";
    }
}
