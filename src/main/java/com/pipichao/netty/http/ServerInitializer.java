package com.pipichao.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //http 编解码器
        pipeline.addLast(new HttpServerCodec());
        //自定义的处理器
        pipeline.addLast(new HttpHandler());
    }
}
