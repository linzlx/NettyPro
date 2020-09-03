package com.study.nio;



import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zzy
 * @time 2020-08-23 9:44)
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws  Exception{

        //创建文件的输入流
        File file = new File("e:\\file01.txt");
        FileInputStream fileInputStream=new FileInputStream(file);

        //通过fileInputStream 获取对应的FileChannel ->实际类型filechannelImpl
        FileChannel fileChannel = fileInputStream.getChannel();

        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());

        //将通道的数据读入到Buffer
        fileChannel.read(byteBuffer);

        //将缓冲区的字节转成String
        System.out.println(new String(byteBuffer.array()));

        fileInputStream.close();

    }
}
