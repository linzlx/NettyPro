package com.study.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author zzy
 * @time 2020-09-05 9:45)
 */
public class NettyByteBuf01 {
    public static void main(String[] args) {
        //创建一个byteBuf
        //说明
        //1，创建对象，该对象含一个数组arr ，是一个byte[10]
        //2，在netty的buffer中不需要使用flip ，进行反转, 底层维护了readerIndex 和 writerIndex
        //3，通过 readerIndex ，writerIndex 和capacity 将buffer分成三个区域
        //0---readerIndex 已经读取的区域
        //readerIndex---writerIndex   可读的区域
        //writerIndex ---capacity  可写的区域
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i <buffer.capacity() ; i++) {
            buffer.writeByte(i);

        }

        //input
        for (int i = 0; i <buffer.capacity() ; i++) {
            System.out.println(buffer.getByte(i));

        }

        //input
        for (int i = 0; i <buffer.capacity() ; i++) {
            System.out.println(buffer.readerIndex());

        }



    }
}
