package com.study.nio.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author zzy
 * @time 2020-08-28 21:36)
 */
public class NewIoServer {
    public static void main(String[] args) throws Exception{
        InetSocketAddress address =new InetSocketAddress(7001);

        ServerSocketChannel serverSocketChannel= ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();

            int readCount = 0;
            while (-1!=readCount){

                try {
                   readCount= socketChannel.read(byteBuffer);
                }catch (Exception e){

                    break;
                }
                //将buffer进行倒带,position=0,mark 作废
                byteBuffer.rewind();
            }
        }
    }

}
