package com.study.nio;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author zzy
 * @time 2020-08-24 10:07)
 *
 * Scattering: 将数据写入到buffer 时，可以采用buffer 数组，依次写入 [分散]
 * Gathering： 从buffer读取数据事，可以采用buffer 数组，依次读
 */
public class ScatteringAndGathering {
    public static void main(String[] args) throws Exception{
        //使用 ServerSocketChannel 和SocketChannel  网络

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        //绑定端口到socket ,并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0]=ByteBuffer.allocate(5);
        byteBuffers[1]=ByteBuffer.allocate(3);

        //等待客户端连接(telnet)
        SocketChannel socketChannel = serverSocketChannel.accept();

        //假定从客户端接受8个字节
        int messageLength  =8;

        //循环读取
        while (true){

            int byteRead =0;

            while (byteRead<messageLength) {
                long l = socketChannel.read(byteBuffers);
                byteRead += l;

                System.out.println("byteRead=" + byteRead);
                //使用流打印，看看当前的buffer的position和limit
                Arrays.asList(byteBuffers).stream().map(buffer -> "positon=" + buffer.position() +
                        ",limit=" + buffer.limit()).forEach(System.out::println);
            }
                //将所有的buffer进行flip
                Arrays.asList(byteBuffers).forEach((buffer ->buffer.flip()));

                // 将我们的数据读出显示到客户端
                long byteWirte=0;
                while (byteWirte<messageLength){
                    long l1 = socketChannel.write(byteBuffers);
                    byteWirte +=l1;
                }

                //将所有的buffer 进行clear
                Arrays.asList(byteBuffers).forEach(buffer-> {
                    buffer.clear();
                });
                System.out.println("byteRead:="+byteRead+"  byteWrite="+byteWirte+",messageLength:"+messageLength);
        }

    }
}
