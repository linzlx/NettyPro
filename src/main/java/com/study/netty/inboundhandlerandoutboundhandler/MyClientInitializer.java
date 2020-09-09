package com.study.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zzy
 * @time 2020-09-08 10:19)
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //出站的handler，进行编码
        pipeline.addLast(new MyLongToByteEncoder());

        pipeline.addLast(new MyByteToLongDecoder());

        pipeline.addLast(new MyClientHandler());
    }
}
