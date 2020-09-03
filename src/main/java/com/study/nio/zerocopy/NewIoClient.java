package com.study.nio.zerocopy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author zzy
 * @time 2020-08-28 22:00)
 */
public class NewIoClient {
    public static void main(String[] args) throws Exception{

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",7001));
        String filename="";

        //
        FileChannel fileChannel = new FileInputStream(filename).getChannel();

        //准备发送
        long startTime = System.currentTimeMillis();

        //在linux下一个transferTo 方法就可以完成
        //在windows 下 一次调用transferTO只能发送8m，就需要分段传输文件，而且要注意传输时的位置
        //transferTo底层用到零拷贝
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        System.out.println("发送的总的字节数="+transferCount+"耗时："+(System.currentTimeMillis()-startTime));

        fileChannel.close();
    }
}
