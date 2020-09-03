package com.study.bio;


import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zzy
 * @time 2020-08-21 19:13)
 */
public class BIOServer  {
    public static void main(String[] args) throws Exception {
        //创建线程池
        //如果有客户连接创建一个线程
        ExecutorService executorService =Executors.newCachedThreadPool();

        ServerSocket serverSocket =new ServerSocket(6666);

        System.out.println("服务器启动了");

        while (true){
            //监听，等待客户端连接
            final   Socket socket= serverSocket.accept();
            System.out.println("连接到一个客户端");

            //创建一个线程，与之通信（单独写一个方法）
            executorService.execute(new Runnable() {
                public void run() {
                    handler(socket);
                }
            });
        }
    }
    //编写一个Handler方法，和客户端通讯
    public static void handler(Socket socket){
        try {
            System.out.println("线程信息 id="+ Thread.currentThread().getId()+"name"+Thread.currentThread().getName());
            byte[] bytes= new byte[1024];
            //通过socket，获取输入流
            InputStream inputStream= socket.getInputStream();

            //循环读取客户端发送的数据
            while (true){
                System.out.println("线程信息 id="+ Thread.currentThread().getId()+"name"+Thread.currentThread().getName());
               int read= inputStream.read(bytes);
               if(read!=-1){
                   System.out.println(new String(bytes,0,read));//输出客户端发送的数据
               }else {
                   break;
               }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
