package com.study.nio;

import javax.sound.midi.Soundbank;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.sql.SQLOutput;

/**
 * @author zzy
 * @time 2020-08-24 9:31)
 * 说明：
 *1，MappedByteBuffer 可以让文件直接在内存（堆外内存）中修改，操作系统不需要拷贝
 *
 */
public class MappedByteBuffer {
    public static void main(String[] args) throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");

        //获取对应的文件通道
        FileChannel Channel = randomAccessFile.getChannel();

        /*
        * 参数1：FileChannel.MapMode.READ_WRITE 使用读写模式
        * 参数2：O  可以直接修改起始位置
        * 参数3：5  是映射到内存的大小，即将1.txt  的多少个字节映射到内存
        * 可以直接修改的范围就是0-5
        * 实际类型是DirectByteBuffer
        */
        java.nio.MappedByteBuffer mappedByteBuffer = Channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0,(byte)'H');
        mappedByteBuffer.put(3,(byte)9);

        randomAccessFile.close();

        System.out.println("修改成功");
    }
}
