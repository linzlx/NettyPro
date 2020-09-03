package com.study.nio;



import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zzy
 * @time 2020-08-25 21:31)
 */
public class NioServer {
    public static void main(String[] args) throws Exception{

        //创建 serverSocketChannel -> serverSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //得到一个selector 对象
        Selector selector = Selector.open();

        //绑定一个端口6666在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //设置非阻塞
        serverSocketChannel.configureBlocking(false);

        //把 serverSocketChannel 注册到 selector 关心事件为 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //
        System.out.println("注册后的selectionKey 数量="+selector.keys().size());

        //循环等待客户端连接
        while (true){

            //wait 1m ,if no event ,back
            if(selector.select(1000)==0){
                System.out.println("服务器等待1秒，无连接");
                continue;
            }

            //如果返回的>0,就获取到相关的selectionKey集合
            //1,如果返回的>0,表示就已经获取关注事件
            //2,selector.selectedKeys() 返回关注事件的集合
            //通过selectionKeys 反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            System.out.println("selectionKeys 数量="+selectionKeys.size());

            //遍历 Set<SelectionKey> ,使用迭代器遍历
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()){
                //获取到selectionKey
                SelectionKey key = keyIterator.next();
                //根据key对应的通道发生的事件做相应的处理
                //如果是OP_ACCEPT，有新的客户端连接
                if(key.isAcceptable()){
                    //该客户端生成一个socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生成一个socketChannel"+socketChannel.hashCode());
                    //将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //将当前的socketChannel 注册到selector,关注事件为OP_READ, 同时给socketChannel关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                    System.out.println("客户端连接后，注册的selectionKey 数量="+selector.keys().size());


                }
                //发生OP_READ
                if(key.isReadable()){
                    //通过key 反向获取对应的channel
                    SocketChannel channel = (SocketChannel)key.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer)key.attachment();
                    channel.read(buffer);
                    System.out.println("客户端发送 ："+new String(buffer.array()));
                }

                //手动从集合中移除当前的selectionKey，防止重复操作
                keyIterator.remove();
            }

        }
    }
}
