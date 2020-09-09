package com.study.netty.codec2;


import com.study.netty.codec.studentPOJO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;


/**
 * @author zzy
 * @time 2020-08-29 12:47)
 */
public class NettyServer {
    public static void main(String[] args) throws Exception {

        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);

        ServerBootstrap bootstrap = new ServerBootstrap();

        //使用链式编程来进行设置,设置两个线程组
        bootstrap.group(boosGroup,workerGroup)
                //使用NioSocketChannel 作为服务器通道的实现
                .channel(NioServerSocketChannel.class)
                //设置线程队列，等待连接的个数
                .option(ChannelOption.SO_BACKLOG,128)
                //设置保持活动连接状态
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                //给workerGroup de eventLoop 对应的管道设置处理器
                //创建一个通道测试对象（匿名对象） childHandler 对应 workerGroup       ,handler 对应 bossGroup
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    //给 pipeline 设置处理器
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //指定对那种对象进行解码
                        pipeline.addLast(new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
                        pipeline.addLast(new NettyServerHandler());
                    }
                });
        System.out.println("....服务器 is ready");

        //绑定端口并且同步，生成一个channelFuture对象,启动服务器
        ChannelFuture cf= bootstrap.bind(6667).sync();


        //对关闭通道进行监听
        cf.channel().closeFuture().sync();


    }
}
