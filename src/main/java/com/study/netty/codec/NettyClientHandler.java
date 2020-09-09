package com.study.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author zzy
 * @time 2020-08-29 22:14)
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当通道就绪就会触发方法*/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //发送一个student 对象到服务器

        studentPOJO.Student student = studentPOJO.Student.newBuilder().setId(4).setName("acdc").build();

        ctx.writeAndFlush(student);
    }

    /**当通道有读取事件时，会触发*/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=(ByteBuf) msg;
        System.out.println("服务器回复的消息："+buf.toString());
        System.out.println("服务器的地址："+ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
