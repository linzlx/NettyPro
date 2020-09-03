package com.study.netty.http;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * @author zzy
 * @time 2020-09-03 14:02)
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        //得到管道
        ChannelPipeline pipeline = ch.pipeline();

        //加入netty 提供的httpServerCode codec(编解码器)
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());

        //增加一个自定义的handler
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());
    }
}
