package com.pipichao.nio;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws Exception{
        //创建服务端
        ServerSocketChannel serverSocketChannel =ServerSocketChannel.open();
        System.out.println("服务端建立成功");
        InetSocketAddress socketAddress = new InetSocketAddress(65535);
        //设置通道为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //绑定监听的端口
        serverSocketChannel.socket().bind(socketAddress);

        //创建选择器
        Selector selector =Selector.open();

        //服务端绑定选择器，设置监听的事件 ：
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        //等待客户端连接
        while (true){
//            //等待1000ms后
//            if (selector.select(1000)==0){
//                System.out.println("还没有客户端连接");
//                continue;
//            }
            if (selector.select()>0){
                //有事件发生的集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
//                Set<SelectionKey> keys = selector.keys();//所有的集合
                Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                while (keyIterator.hasNext()){
                    SelectionKey key = keyIterator.next();
                    //根据不同的事件做不同的处理

                    //如果是accept事件
                    if (key.isAcceptable()) {
                        //获取到当前客户端的通道
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);

                        //将获取到的通道绑定到选择器,绑定监听事件为 ：OP_READ,并且分配一个缓存
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    }

                    //如果是读事件
                    if (key.isReadable()){
                        //获取到通道
                        SocketChannel channel =(SocketChannel) key.channel();
                        //获取到缓存
                        ByteBuffer buffer= (ByteBuffer) key.attachment();

                        //数据从通道中读取到缓存
                        channel.read(buffer);

                        System.out.println("收到的数据："+new String(buffer.array()));

                    }

                    //手动清除被操作过的key，防止重复操作
                    keyIterator.remove();
                }


            }
        }


    }
}
