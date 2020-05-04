package com.pipichao.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 管道准备好之后就会触发
     * */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server ", CharsetUtil.UTF_8));
    }

    /**
     *管道有数据读取事件时候触发
     * */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("收到服务器端的回复："+byteBuf.toString());
    }

    /**
     * 出现异常的回掉
     * */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //打印异常信息
        cause.printStackTrace();
//        关闭
//        ctx.close();
        ctx.channel().close();
    }
}
