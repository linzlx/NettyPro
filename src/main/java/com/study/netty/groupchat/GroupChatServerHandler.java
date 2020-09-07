package com.study.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zzy
 * @time 2020-09-05 10:48)
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    /**定义一个channel组，管理所有的channel
     *GlobalEventExecutor.INSTANCE  是全局的事件执行器，是一个单例
     */
    //使用hashMap 管理

    public  static Map<String ,Channel> channels =new HashMap<String,Channel>();
    public  static Map<User ,Channel> channels2 =new HashMap<User,Channel>();


    private static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**handlerAdded 表示连接建立，一旦连接，第一个被执行
     * 将当前channel 加入到 ChannelGroup
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入聊天的信息推送给其他在线的客户端
        //该方法会将ChannelGroup 中所有的Chanel你遍历 ，并发送
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+"加入聊天"+sdf.format(new Date()+"\n"));
        channelGroup.add(channel);

        channels.put("id100",channel);
       // channels2.put(new User(10,"123"),channel);
    }

    /**表示断开连接，将xx客户离开信息推送给当前在线的客户*/
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+"离开了\n");
        System.out.println("ChannelGroup size"+channelGroup.size());
    }

    /**表示Channel处于活动状态，提示 xx上线 */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"上线了~");
    }

    /**示Channel处于非活动状态，提示 xx下线*/
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"离线了~");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        Channel channel = ctx.channel();
        //遍历
        channelGroup.forEach(ch -> {
            //不是当前channel，直接转发
            if(channel!=ch){
                ch.writeAndFlush("[客户]"+channel.remoteAddress()+"发送了消息"+msg+"\n");
            }else {
                ch.writeAndFlush("[自己]发送了消息"+msg);
            }
        });
}

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
