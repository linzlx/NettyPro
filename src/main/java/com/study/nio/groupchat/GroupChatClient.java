package com.study.nio.groupchat;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author zzy
 * @time 2020-08-26 11:49)
 */
public class GroupChatClient {

    /**
     *定义相关的属性
     * HOST： 服务器ip
     * PORT: 服务器端口
     */
    private final String HOST ="127.0.0.1";
    private final int PORT=6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName ;

    public GroupChatClient() throws IOException {

        selector = Selector.open();
        //连接服务器
        socketChannel=socketChannel.open(new InetSocketAddress("127.0.0.1",PORT));

        socketChannel.configureBlocking(false);

        //将channel 注册到selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        //得到username
        userName =socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(userName+"is ok");

    }
    /**向服务器发送消息*/
    public  void sendInfo(String info){
        info =userName+":"+info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**c读取从服务器端回复的消息*/
    public void readInfo(){
        try {

            int readChannels = selector.select();

            if(readChannels>0){

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){

                    SelectionKey key = iterator.next();

                    if(key.isReadable()){
                        //得到相关的通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        //得到buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取
                        sc.read(buffer);
                        //把读到的缓冲区的数据转成字符串

                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());

                    }
                    //删除当前的selectionKey
                    iterator.remove();
                }

            } else {

            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        //启动客户端
        GroupChatClient chatClient = new GroupChatClient();

        //启动一个线程,每隔3秒读取从服务器端发送的数据
       /* new Thread() {
            public void run() {

                while (true) {
                    chatClient.readInfo();
                    try {
                        Thread.currentThread().sleep(3000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();*/
        //发送给服务端
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }
    }


}
