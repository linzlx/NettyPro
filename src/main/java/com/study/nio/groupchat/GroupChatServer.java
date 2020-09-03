package com.study.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author zzy
 * @time 2020-08-26 10:37)
 */
public class GroupChatServer {
    /**定义属性*/
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT =6667;

    /**构造器初始化*/
    public GroupChatServer() {

        try {
           //得到选择器
           selector= Selector.open();
           //ServerSocketChannel
           listenChannel= ServerSocketChannel.open();
           //bind port
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将listenChannel 注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /** 监听*/
    public void listen(){

        System.out.println("监听线程: " + Thread.currentThread().getName());
        try {
            //循环处理
            while (true){
                int count = selector.select();
                //有事件处理
                if (count>0){
                    //遍历得到selectorKey 集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        //取出selectorKey
                        SelectionKey key = iterator.next();

                        if(key.isAcceptable()){
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            //将 sc 注册到selector
                            sc.register(selector,SelectionKey.OP_READ);
                            //提示
                            System.out.println(sc.getRemoteAddress()+"上线了");
                        }

                        if(key.isReadable()){

                            //处理读
                            readData(key);
                        }

                        //当前的key删除，防止重复处理
                        iterator.remove();
                    }
                }else {
                    System.out.println("等待....");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //
        }
    }
    /**读取客户端消息*/
    public void  readData(SelectionKey key){
        //定义一个socketChannel
        SocketChannel channel =null;
        try {
            //得到channel
            channel = (SocketChannel)key.channel();
            //
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);
            //根据count的值做处理
            if(count>0){
                //把缓冲区的数据转成字符串
                String msg = new String(buffer.array());
                //输出该消息
                System.out.println("from 客户端"+msg);

                //向其它的客户端转发消息
                sendInfoToOtherClients(msg,channel);
            }

        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress()+"离线了");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }
    }
    /**转发消息给其它客户（通道）*/
    private void sendInfoToOtherClients(String msg,SocketChannel self) throws IOException {

        System.out.println("服务器转发消息中...");

        System.out.println("服务器转发数据给客户端线程: " + Thread.currentThread().getName());
        //遍历，所有注册到selector 上的 socketChannel， 并排除self
        for(SelectionKey key: selector.keys()){

            //通过key 取出对应的socketChannel
            Channel targetChannel = key.channel();

            //排除self
            if(targetChannel instanceof SocketChannel && targetChannel!=self){

                //转型
                SocketChannel dest = (SocketChannel)targetChannel;
                //将msg 存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer 的数据写入 通道
                dest.write(buffer);


            }

        }

    }

    public static void main(String[] args) {

        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();

    }
}
