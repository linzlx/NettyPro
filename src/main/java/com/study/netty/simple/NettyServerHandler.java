package com.study.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author zzy
 * @time 2020-08-29 14:53)
 *
 *
 * 说明
 * 1，自定义一个Handler ，需要继续netty规定好的HandlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**读取数据实际，（读取客户端发送的消息）
    * 1，ChannelHandlerContext ctx:上下文对象，含有管道pipeline，channel,地址
    * 2.object msg:客户端发送的数据，默认Object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
        /*
        * 异步防阻塞方案1
        * taskQueue
        * */

        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10*1000);
                }catch (Exception e){
                    System.out.println("发生异常"+e.getMessage());
                }


            }
        });



        //用户自定义定时任务 scheduleTaskQueue
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10*1000);
                }catch (Exception e){
                    System.out.println("发生异常"+e.getMessage());
                }


            }
        },5, TimeUnit.SECONDS);









        System.out.println("服务器读取线程"+Thread.currentThread().getName());
        System.out.println("server ctx"+ctx);

        //将msg转成一个ByteBuf
        ByteBuf buf=(ByteBuf)msg;

        System.out.println("客户端发送消息是："+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客服端地址："+ctx.channel().remoteAddress());

    }
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
