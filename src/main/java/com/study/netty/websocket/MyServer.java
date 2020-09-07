package com.study.netty.websocket;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * @author zzy
 * @time 2020-09-06 20:08)
 */
public class MyServer  {
    public static void main(String[] args) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //在bossGroup增加日志处理器
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //加入netty 提供的httpServerCode codec(编解码器)
                            pipeline.addLast(new HttpServerCodec());
                            //以块方式写，添加ChuckedWrite 处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            /**说明
                             * 1，http数据在传输过程中是分段，HttpObjectAggregator ，就可以将多个段聚合，
                             * 2，这就是为什么当浏览器发送大量数据时，就会发送多次http请求
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /**说明
                             *1,对于webSocket ，他的数据是以帧（frame）形式传递
                             *2,可以看到webSocketFrame 下面有六个子类
                             *3,浏览器请求时 ws://localhost:6667/hello （uri）
                             *4,WebSocketServerProtocolHandler 核心功能时将http协议升级为ws协议，保持长连接
                             * 5，通过一个状态码 101
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                            pipeline.addLast(new MyTestWebSocketFrameHandler());

                        }
                    });


            ChannelFuture channelFuture = b.bind(6667).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
