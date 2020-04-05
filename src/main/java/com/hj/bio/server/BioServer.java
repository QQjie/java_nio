package com.hj.bio.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {
    public static void main(String[] args){
        try {
            ServerSocket serverSocket=new ServerSocket();
            serverSocket.bind(new InetSocketAddress(8083));

            ExecutorService executorService = Executors.newFixedThreadPool(5);
            System.out.println("-----------------");
            while(true){
                System.out.println("---------接受请求---------");
               final Socket accept = serverSocket.accept();

                System.out.println("------------等待数据-------------");
                executorService.execute(new Runnable() {
                    public void run() {
                        while(true){

                            try {
                                InputStream inputStream = accept.getInputStream();
                                byte[] buff = new byte[1024];
                                int data = inputStream.read(buff);
                                System.out.println(new String(buff,0,data));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
