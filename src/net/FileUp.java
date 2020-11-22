package net;


/*
* 文件上传案例的客户端： 读取本地文件，上传到服务器，读取服务器回写的数据；
*       明确：
*           数据源；
*           目的地：服务器；
*  实现步骤：
*       1.创建本地字节输入流FileInputStream对象，构造方法中绑定要读取的数据源；、
*       2.创建一个客户端Socket对象，构造方法中绑定服务器的ip地址和端口号；
*       3.使用Socket 中的方法获取网络输出流对象；
*       4.使用本地FileIntputStream对象中的read方法读取本地文件；
*       5. 使用网络字节输出流对象中的write方法，把本地文件上传到服务器；
*       6.使用Socket 中的方法获取网络输入流对象；
*       7.使用网络输入流对象中的read方法读取服务器回写的数据；
*       8.释放资源；（本地流和网络流）
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FileUp {
    public static void main(String[] args) throws IOException {
        //1.创建本地字节输入流FileInputStream对象，构造方法中绑定要读取的数据源；、
        FileInputStream fis=new FileInputStream("D:\\Imag\\girl.jpg");
        // 2.创建一个客户端Socket对象，构造方法中绑定服务器的ip地址和端口号；
        /*Socket socket=new Socket("10.234.90.15",8888);*/
        Socket socket=new Socket("127.0.0.1",8888);
        //3.使用Socket 中的方法获取网络输出流对象；
        OutputStream os=socket.getOutputStream();
        // 4.使用本地FileIntputStream对象中的read方法读取本地文件；
        int len=0;
        byte[] bytes= new byte[1024];
        while((len=fis.read(bytes))!=-1){
            //fis.read(bytes)读取本地文件，结束标记是读取到-1结束，while循环里不会读取-1。也不会写到服务器中
            os.write(bytes,0,len);
        }
        //解决方法：上传完文件，给服务器写一个结束标记；

        socket.shutdownOutput();
        //6.使用Socket 中的方法获取网络输入流对象；
        InputStream is=socket.getInputStream();


        //7.使用网络输入流对象中的read方法读取服务器回写的数据；
        while((len=is.read(bytes))!=-1){
            System.out.println(new String(bytes,0,len));
        }

        /*System.out.println("2222222222");*/
        //8.释放资源；（本地流和网络流）
        fis.close();
        socket.close();

    }
}
