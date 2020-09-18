package com.study.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author zzy
 * @time 2020-09-09 10:23)
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("客户端接收到消息如下");
        System.out.println("长度="+len);
        System.out.println("内容"+new String(content,CharsetUtil.UTF_8));
        System.out.println("客户端接收的消息包数量="+(++this.count));

    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i <5 ; i++) {
            String msg="你好帅，？？？";
            byte[] content =msg.getBytes(CharsetUtil.UTF_8);
            int length =msg.getBytes(CharsetUtil.UTF_8).length;

            //创建协议包对象
            MessageProtocol messageProtocol=new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常消息="+cause.getMessage());
        ctx.close();
    }


}
