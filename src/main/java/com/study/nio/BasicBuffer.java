package com.study.nio;

import java.nio.IntBuffer;

/**
 * @author zzy
 * @time 2020-08-21 21:20)
 */
public class BasicBuffer {

    public static void main(String[] args) {
        //举例说明buffer的使用(简单)
        //创建一个buffer，大小为5，即可以存放给五个int
        IntBuffer intBuffer=IntBuffer.allocate(5);
        //向buffer里存放数据
//        intBuffer.put(10);
//        intBuffer.put(11);
//        intBuffer.put(12);
//        intBuffer.put(13);
//        intBuffer.put(14);

        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i*2);
        }
        //如何从buffer读取数据
        //将buffer转换，读写切换
        intBuffer.flip();

        while(intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }

    }

}
