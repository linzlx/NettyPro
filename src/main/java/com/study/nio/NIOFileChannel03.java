package com.study.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zzy
 * @time 2020-08-23 10:07)
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws  Exception{

        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        //循环读取
        while (true){

            //这里有个重要操作
            byteBuffer.clear();//重置position，limit

            int read = fileChannel01.read(byteBuffer);
            //
            if (read==-1){//表示读完

            break;
            }
            //将buffer 中的数据写入到filechanne02
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }

        //关闭流
        fileInputStream.close();
        fileOutputStream.close();

    }
}
