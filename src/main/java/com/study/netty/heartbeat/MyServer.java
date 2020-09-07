package com.study.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author zzy
 * @time 2020-09-06 18:55)
 */
public class MyServer {
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
                            /**
                             1.加入一个netty提供的 IdleStateHandler
                            2. long readerIdleTime, 表示多长时间没有读，会发送一个心跳检测包，检测是否连接
                            3. long writerIdleTime, 表示多长时间没有写，会发送一个心跳检测包，检测是否连接
                            4. long allIdleTime,   表示多长事件没有读写，会发送一个心跳检测包，检测是否连接


                             5.Triggers an {@link IdleStateEvent} when a {@link Channel} has not performed
                             * read, write, or both operation for a while.
                             *
                             * 6.当IdleStateHandler 触发后 ，就会传递给管道 的下一个handler去处理
                             * 通过调用（触发）下个handler的 userEventTiggered 处理
                             */

                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            //加入一个对空闲检测进一步处理的自定义handler
                            pipeline.addLast(new MyServerHandler());
                        }
                    });


            ChannelFuture channelFuture = b.bind(6668).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
