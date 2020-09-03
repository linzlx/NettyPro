package com.study.netty.simple;


import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * @author zzy
 * @time 2020-08-29 12:47)
 */
public class NettyServer {
    public static void main(String[] args) throws Exception {

        /*创建BossGroup 和 WorkerGroup
        * 说明
        * 1，创建两个线程组
        * 2，boos 只是处理连接请求，真正的客户端处理交给workerGroup
        * 3,两个都是无限循环
        * 4，两个含有的子线程（NioEventLoop）的个数默认实际cpu核数*2
         */

        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);

        //创建服务器端启动的对象,去配置参数
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
                //创建一个通道测试对象（匿名对象）
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    //给 pipeline 设置处理器
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyServerHandler());
                    }
                });
        System.out.println("....服务器 is ready");

        //绑定端口并且同步，生成一个channelFuture对象,启动服务器
        ChannelFuture cf= bootstrap.bind(6667).sync();

        //给 cf 注册监听器，监控关心的事件
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(cf.isSuccess()){
                    System.out.println("监听端口 6668 成功");
                }else {
                    System.out.println("监听端口 6668 失败");
                }
            }
        });

        //对关闭通道进行监听
        cf.channel().closeFuture().sync();


    }
}
