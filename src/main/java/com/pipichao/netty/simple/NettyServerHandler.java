package com.pipichao.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义的处理器
 *
 * */

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("注册一个：管道"+ctx);
    }

    //读取实际的数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("管道上下文："+ctx);

        ///这里使用的是 netty 提供的类
        ByteBuf byteBuf =(ByteBuf)msg;
        System.out.println("收到来自客户端的消息："+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println(ctx.channel().remoteAddress());


        /**
         * 模拟处理请求很长
         *
         * 加入任务队列
         *
         * */
//        ctx.pipeline().channel().eventLoop().execute(new Runnable() {
//            public void run() {
//                try {
//                    Thread.sleep(10*1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        System.out.println("正在给客户端回复");
        //将数据写入到缓冲，并且刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 我已收到消息",CharsetUtil.UTF_8));
    }


    //出现异常的时候,通常是关闭管道

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
//        ctx.close();
        //这种方式也可以
        ctx.channel().close();
    }
}
