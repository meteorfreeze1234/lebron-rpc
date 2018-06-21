package com.lebron.api;

import java.io.Serializable;

/**
 * @author: shenggao
 * date: 2018/6/21
 */
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = -9100893052391757993L;
    private String methodName;
    private String className;
    private Object[] params;
    private Class<?>[] paramsType;

    public Class<?>[] getParamsType() {
        return paramsType;
    }

    public void setParamsType(Class<?>[] paramsType) {
        this.paramsType = paramsType;
    }


    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

}
