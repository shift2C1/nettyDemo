package com.pipichao.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HttpHandler extends SimpleChannelInboundHandler<HttpObject> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) throws Exception {
        //判断是否是 http 请求
        if (msg instanceof HttpRequest){

            System.out.println("客户端地址"+channelHandlerContext.channel().remoteAddress());

            //响应给浏览器
            ByteBuf byteBuf = Unpooled.copiedBuffer("it is respones",CharsetUtil.UTF_8);

            HttpResponse httpResponse =new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,byteBuf);

            //设置响应头
            httpResponse.headers().add(HttpHeaderNames.CONTENT_TYPE,"text/plain")
                    .add(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());


            //返回数据
            channelHandlerContext.writeAndFlush(httpResponse);

        }


    }
}
