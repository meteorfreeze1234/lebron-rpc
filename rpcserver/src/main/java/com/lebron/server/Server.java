package com.lebron.server;

import com.lebron.api.IHello;

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
    ExecutorService executorService = Executors.newCachedThreadPool();

    public void publish() {
        registryService();
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("服务启动了");
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new RequestHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registryService() {
        IHello iHello = new IHelloImpl();
        RequestHandler.objectMap.put(IHello.class.getName(), iHello);
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.publish();
    }
}
