package com.study.nio;

import java.nio.ByteBuffer;

/**
 * @author zzy
 * @time 2020-08-24 9:23)
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        //创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        for (int i = 0; i <64 ; i++) {
            byteBuffer.put((byte)i);

        }
        byteBuffer.flip();

        //得到只读buffer
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();

        //读取
        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
    }
}

