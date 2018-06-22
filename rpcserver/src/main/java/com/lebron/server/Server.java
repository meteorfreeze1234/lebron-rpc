package com.lebron.server;

import com.lebron.api.IHello;
import com.lebron.server.zk.IRegistryCenter;
import com.lebron.server.zk.IRegistyCenterImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: shenggao
 * date: 2018/6/21
 */
public class Server {
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private IRegistryCenter registryCenter;

    private int port;

    public Server(IRegistryCenter registryCenter, int port) {
        this.registryCenter = registryCenter;
        this.port = port;
    }

    public void publish() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务启动了");

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new RequestHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registryService(IHello iHello, Class<?> interfaceClazz) {
        RequestHandler.objectMap.put(interfaceClazz.getName(), iHello);
        registryCenter.registry(interfaceClazz.getName(), "127.0.0.1:" + port);
    }
}
