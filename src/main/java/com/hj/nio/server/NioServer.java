package com.hj.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class NioServer {
    public static void main(String[] args) {
        try {
            List<SocketChannel> list = new ArrayList<>();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8084));
            serverSocketChannel.configureBlocking(false);
            while (true) {
                ByteBuffer buff = ByteBuffer.allocate(1024);
                SocketChannel socketChannel = serverSocketChannel.accept();

                if (socketChannel == null) {
                    for (SocketChannel channel : list) {
                        int data = channel.read(buff);
                        if (data > 0) {
                            System.out.println(new String(buff.array(),0,data));
                        }
                    }
                } else {
                    socketChannel.configureBlocking(false);
                    list.add(socketChannel);
                    int data = socketChannel.read(buff);
                    if (data > 0) {
                        System.out.println(new String(buff.array(),0,data));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
