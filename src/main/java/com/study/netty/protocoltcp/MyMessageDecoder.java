package com.study.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author zzy
 * @time 2020-09-09 11:18)
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder 被调用");
        //二进制字节码-》 MessageProtocol 数据包
        int length =in.readInt();

        byte[] content =new byte[length];
        in.readBytes(content);

        //封装成MessageProtocol 对象 放入out 传给下一个handler
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(content);
        out.add(messageProtocol);
    }
}
