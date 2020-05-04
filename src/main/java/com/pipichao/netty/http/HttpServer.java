package com.pipichao.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/***
 * netty 实现http服务
 *
 */
public class HttpServer {
    public static void main(String[] args) throws Exception{
        NioEventLoopGroup boss =new NioEventLoopGroup(1);
        NioEventLoopGroup worker =new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap =new ServerBootstrap();
            serverBootstrap.group(worker,boss)
                    .channel(NioServerSocketChannel.class)

                    .childHandler(new ServerInitializer());

            System.out.println("http server is ready");
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();//

            System.out.println("http server started");


            channelFuture.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            System.out.println("http server closed");
        }
    }
}
