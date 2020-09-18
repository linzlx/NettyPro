package com.study.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @author zzy
 * @time 2020-09-09 10:27)
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("服务器接收到信息如下");
        System.out.println("长度"+len);
        System.out.println("内容"+new String(content,CharsetUtil.UTF_8));
        System.out.println("服务器接受到消息包数量"+(++this.count));


        //回复
        String responseContent=UUID.randomUUID().toString();
        int responseLen=responseContent.getBytes("utf-8").length;

        byte[] responseContentBytes = responseContent.getBytes("utf-8");

        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(responseLen);
        messageProtocol.setContent(responseContentBytes);

        ctx.writeAndFlush(messageProtocol);
    }
}
