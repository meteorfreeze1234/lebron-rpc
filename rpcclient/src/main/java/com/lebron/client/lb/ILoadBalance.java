package com.lebron.client.lb;

import java.util.List;

/**
 * @author: shenggao
 * date: 2018/6/22
 */
public interface ILoadBalance {
    String selectHost(List<String> addressList);
}
