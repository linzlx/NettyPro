package com.study.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author zzy
 * @time 2020-08-29 14:53)
 *

 */
//public class NettyServerHandler extends ChannelInboundHandlerAdapter {
public class NettyServerHandler extends SimpleChannelInboundHandler<studentPOJO.Student> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, studentPOJO.Student msg) throws Exception {

        System.out.println("客户端发送的数据 id="+msg.getId()+" 名字="+msg.getName() );
    }

    /* @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
        //读取从客户端发送的StudentPOJO.Student

        studentPOJO.Student student=(studentPOJO.Student)msg;

        System.out.println("客户端发送的数据 id="+student.getId()+" 名字="+student.getName() );

    }*/
    /***/
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端:!!!", CharsetUtil.UTF_8));
    }


    /**处理异常*/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
