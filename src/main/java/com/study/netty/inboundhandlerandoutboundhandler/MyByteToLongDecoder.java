package com.study.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author zzy
 * @time 2020-09-08 10:10)
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     * list 集合 ，将解码后的数据传给下一个handler
     *
     * decode 会根据接受的数据，被调用多次，
     * 调用完成将由list传递给channelInBoundHandler ，该处理器也会被调用多次
     * decode->channelInBoundHandler  decode->channelInBoundHandler
     *
     *
     * */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //因为Long 8个字节 ，需要判断有8个字节，才能读取一个Long
        System.out.println("MyByteToLongDecoder 被调用 \n进行解码");
        if(in.readableBytes()>=8){
            out.add(in.readLong());
        }
    }
}
