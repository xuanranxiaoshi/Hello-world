package net;

/*
* 文件上传案例的服务器端：读取客户端上传的文件，保存到服务器的硬盘，给客户端回写“上传成功”
*       明确：
*           数据源：客户端上传的文件；
*           目的地：服务器的硬盘
*
*   实现步骤：
*       1.创建服务器ServerSocket对象，和系统要指定的端口
*       2.使用ServerSocket对象中的accept，获取到请求的客户端对象；
*       3.使用Socket对象中的方法获得网络字节输入流对象；
*       4.判断本地目标文件夹是否存在，不存在构建；
*       5.创建一个本地字节输出流FileOutputstream对象，绑定输出目的地；
*       6。使用网络字节输入流中的read客户端上传的文件
*       7.使用本地字节输出流中的write方法，把读取的文件保存到本地硬盘；
*       8.使用Socket对象中的方法获得网络输出流对象；
*       9.使用网络输出流对象中的write方法给客户端会写“上传成功”；
*       10。释放资源（Socket，FileInputstream）；
*
*
* */

import javax.naming.ldap.SortKey;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class FileSever {
    public static void main(String[] args) throws IOException {
        //1.创建服务器ServerSocket对象，和系统要指定的端口
        ServerSocket serve=new ServerSocket(8888);
        //2.使用ServerSocket对象中的accept，获取到请求的客户端对象；
        /**
         *让服务器一直处于监听状态（死循环accept方法）
         * 有一个客户端上传文件，就保存一个文件；
         */

        while (true) {
            Socket socket = serve.accept();
            /**
            * 使用多线程技术，提高程序的效率*
            *有一个客户端上传文件，就开启一个线程，完成文件的上传；
            *
            *
            * */

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        // 3.使用Socket对象中的方法获得网络字节输入流对象；
                        InputStream is = socket.getInputStream();
                        //4.判断本地目标文件夹是否存在，不存在构建；
                        File file = new File("D:\\Image_copy");
                        if (!file.exists()) {
                            file.mkdirs();
                        }

                        /**
                         * 自定义一个文件命名规则：防止同名文件被覆盖
                         * 规则：域名+毫秒值+随机数；
                         *
                         */
                        String filename = "cqu" + System.currentTimeMillis() + new Random().nextInt() + ".jpg";

                        //5.创建一个本地字节输出流FileOutputstream对象，绑定输出目的地；
                        FileOutputStream fos = new FileOutputStream(file+File.separator+filename);

                        //6。使用网络字节输入流中的read客户端上传的文件
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = is.read(bytes)) != -1) {
                            //7.使用本地字节输出流中的write方法，把读取的文件保存到本地硬盘；
                            fos.write(bytes, 0, len);
                        }

                        /*System.out.println("44444444");*/

                        //8.使用Socket对象中的方法获得网络输出流对象；
                        OutputStream os = socket.getOutputStream();
                        //9.使用网络输出流对象中的write方法给客户端会写“上传成功”；
                        os.write("上传成功".getBytes());
                        // 10。释放资源（Socket，FileInputstream）；
                        fos.close();
                        socket.close();
                    }catch (IOException e){
                        System.out.println(e);

                    }
                }
            }).start();


        }
           //socket.close();
    }

}
