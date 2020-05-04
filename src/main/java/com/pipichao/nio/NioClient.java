package com.pipichao.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel =SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 65535));
        if (socketChannel.finishConnect()){
            System.out.println("连接成功");
            String s= "hello";
            ByteBuffer buffer =ByteBuffer.wrap(s.getBytes());

            socketChannel.write(buffer);
        }

        //程序停到这里
        System.in.read();

    }
}
