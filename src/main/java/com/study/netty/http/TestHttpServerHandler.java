package com.study.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author zzy
 * @time 2020-09-03 14:00)
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**
     * SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter 的子类
     * HttpObject ：客户端和服务器相互通讯的数据被封装成 HttpObject*/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        //判断msg 是不是HttpRequest请求
        if(msg instanceof HttpRequest){


            System.out.println("pipeline hashCode"+ctx.pipeline().hashCode()+"TestHttpServerHandler hash="+this.hashCode() );


            System.out.println("msg 类型="+msg.getClass());
            System.out.println("客户端地址："+ctx.channel().remoteAddress());

            //获取到
            HttpRequest httpRequest =(HttpRequest) msg;
            //获取uri      过滤指定资源
            URI uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求了 favicon.ico ，不做相应");
                return;
            }

            //回复信息给浏览器[http协议]
            ByteBuf content = Unpooled.copiedBuffer("hello 我是服务器", CharsetUtil.UTF_8);

            //构造一个http的相应 即httpResponse
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            //将response返回
            ctx.writeAndFlush(response);


        }
    }
}
