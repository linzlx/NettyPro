package com.study.netty.codec2;

import com.google.protobuf.Message;
import com.study.netty.codec.studentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author zzy
 * @time 2020-08-29 14:53)
 *

 */
//public class NettyServerHandler extends ChannelInboundHandlerAdapter {
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

        MyDataInfo.MyMessage.DataType dateType = msg.getDateType();
        if(dateType== MyDataInfo.MyMessage.DataType.StudentType){
            MyDataInfo.Student student= msg.getStudent();
            System.out.println("学生id="+student.getId()+"name="+student.getName());

        }else if(dateType== MyDataInfo.MyMessage.DataType.WorkerType){
            MyDataInfo.Worker worker=msg.getWorker();
            System.out.println("工人名字="+worker.getName()+"年龄="+worker.getAge());
        }else{
            System.out.println("传输的类型不正确");
        }

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
