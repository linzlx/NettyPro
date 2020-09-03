package com.study.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author zzy
 * @time 2020-08-24 9:04)
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws  Exception{
        //创建相关流
        FileInputStream fileInputStream = new FileInputStream("e:\\a.jpg");

        FileOutputStream fileOutputStream = new FileOutputStream("e:\\a2.jpg");

        //获取filechannel
        FileChannel sourcech = fileInputStream.getChannel();
        FileChannel destch = fileOutputStream.getChannel();

        //调用transferFrom完成拷贝
        destch.transferFrom(sourcech,0,sourcech.size());

        //关闭通道和流
        sourcech.close();
        destch.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
