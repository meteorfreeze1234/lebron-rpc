package com.lebron.client.lb;

import java.util.List;
import java.util.Random;

/**
 * @author: shenggao
 * date: 2018/6/22
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    public String doSelect(List<String> addressList) {
        return addressList.get(new Random().nextInt(addressList.size()));
    }
}
