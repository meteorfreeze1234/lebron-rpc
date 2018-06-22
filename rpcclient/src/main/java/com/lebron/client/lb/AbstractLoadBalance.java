package com.lebron.client.lb;

import java.util.List;

/**
 * @author: shenggao
 * date: 2018/6/22
 */
public abstract class AbstractLoadBalance implements ILoadBalance{
    @Override
    public String selectHost(List<String> addressList) {
        if (addressList.isEmpty()) {
            return null;
        }
        if (addressList.size() == 1) {
            return addressList.get(0);
        }
        return doSelect(addressList);
    }

    protected abstract String doSelect(List<String> addressList);
}
