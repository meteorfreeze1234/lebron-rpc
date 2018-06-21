package com.lebron.server;

import com.lebron.api.RpcRequest;
import com.lebron.api.RpcService;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: shenggao
 * date: 2018/6/21
 */
public class RequestHandler extends Thread {
    public static Map<String, RpcService> objectMap =  new HashMap<>();
    private Socket socket;
    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            InputStream inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object object = invoke(rpcRequest);
            if (object != null) {
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(object);
                objectOutputStream.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private Object invoke(RpcRequest request) {
        try {
            RpcService rpcService = objectMap.get(request.getClassName());
            Class clazz = Class.forName(request.getClassName());
            Method method= clazz.getMethod(request.getMethodName(), request.getParamsType());
            return method.invoke(rpcService, request.getParams());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
