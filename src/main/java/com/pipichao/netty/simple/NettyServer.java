package com.pipichao.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws Exception{
        //创建netty的两个线程组
        NioEventLoopGroup boss =new NioEventLoopGroup(1);
        NioEventLoopGroup worker =new NioEventLoopGroup(8);
        try {


            //构建启动参数
            ServerBootstrap serverBootstrap =new ServerBootstrap();
            serverBootstrap.group(boss,worker)//绑定线程组
                    .channel(NioServerSocketChannel.class)//设置通道的实现类
                    .option(ChannelOption.SO_BACKLOG,128)//队列得到的连接数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//保持活动的连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //通过通道获取到管道
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //绑定我们自定义的处理器
                            pipeline.addLast(new NettyServerHandler());

                        }
                    });//给 work 对应的管道设置处理器
//                .bind("127.0.0.7",65535)
            System.out.println("server is ready");

            //绑定端口，生成一个 channelFuture 对象
            ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();
            System.out.println("server is started");
            //监听关闭管道
            channelFuture.channel().closeFuture().sync();
        }finally {
            //优雅的关闭
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            System.out.println("server is closed");
        }


    }
}
