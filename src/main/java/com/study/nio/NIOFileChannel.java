package com.study.nio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zzy
 * @time 2020-08-23 9:12)
 */
public class NIOFileChannel {
    public static void main(String[] args) throws Exception {
        File file =new File("e:\\file01.txt");
        if (!file.exists()) {
                file.createNewFile();
            }

            String str="hello niofilechannel";
        //创建一个输出流
        FileOutputStream fileOutputStream =new FileOutputStream(file);
        //通过fileOutputStream 获取对应的 FileChannel
        //这个fileChannel真实类型是 FileChannalImpl
        FileChannel  fileChannel= fileOutputStream.getChannel();

        //创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将str放入到ByteBuffer
        byteBuffer.put(str.getBytes());

        //对bytebuffer 进行反转
        byteBuffer.flip();

        //将bytebuffer 数据写入到filechannel
        fileChannel.write(byteBuffer);

        fileOutputStream.close();

    }
}
