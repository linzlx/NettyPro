package com.study.netty.codec2;

import com.study.netty.codec.studentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Random;

/**
 * @author zzy
 * @time 2020-08-29 22:14)
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当通道就绪就会触发方法*/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //随机的发送Student 或者 Worker 对象
        int random=new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage=null;

        if (0==random){
            myMessage=MyDataInfo.MyMessage.newBuilder().setDateType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(5).setName("ace").build()).build();
        }else {
            myMessage=MyDataInfo.MyMessage.newBuilder().setDateType(MyDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setName("acd").setAge(18).build()).build();
        }
        ctx.writeAndFlush(myMessage);


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
