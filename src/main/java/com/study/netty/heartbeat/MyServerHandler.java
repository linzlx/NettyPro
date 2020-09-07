package com.study.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import jdk.jfr.EventType;

/**
 * @author zzy
 * @time 2020-09-06 19:27)
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * ctx 上下文
     *evt 事件
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent){

            //将event向下转型 idleStateEvent
            IdleStateEvent event=(IdleStateEvent) evt;
            String eventType=null;
            switch (event.state()){
                case READER_IDLE:
                    eventType="读空闲";
                    break;
                case WRITER_IDLE:
                    eventType="写空闲";
                    break;
                case ALL_IDLE:
                    eventType="读写空闲";
                    break;
                default:
            }
            System.out.println(ctx.channel().remoteAddress()+"--超时时间--"+eventType);
            System.out.println("服务器做出相应处理");
        }
    }
}
