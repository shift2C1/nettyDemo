package com.pipichao.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 客户端
 * */
public class NettyClient {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup eventExecutors =new NioEventLoopGroup();

        try {
            Bootstrap bootstrap =new Bootstrap();

            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("client is ready");

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            System.out.println("connected to server");

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
//            eventExecutors.shutdownGracefully();
            System.out.println("client is closed");
        }

    }
}
