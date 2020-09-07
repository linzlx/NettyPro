package com.study.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.Buffer;
import java.nio.charset.Charset;

/**
 * @author zzy
 * @time 2020-09-05 10:03)
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {

        //create ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world", Charset.forName("utf-8"));

        //
        if(byteBuf.hasArray()){

            byte[] content = byteBuf.array();

            //将content 转成字符串
            System.out.println(new String(content, Charset.forName("utf-8")));

            System.out.println("byteBuf="+byteBuf);

            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());

            //可读的字节数 12
            int len = byteBuf.readableBytes();
            System.out.println("len="+len);

            System.out.println(byteBuf.getByte(0)+"h 104");
            for (int i = 0; i <len ; i++) {
                System.out.println((char)byteBuf.getByte(i));

            }

            System.out.println(byteBuf.getCharSequence(0,4,Charset.forName("utf-8")));
        }

    }
}
