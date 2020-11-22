package net;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
    TCP通信的服务端：接受客户端的请求，读取客户端发送的数据，给客户端回写数据；
    表示服务器的类：
        java.net.ServerSocket: 此类实现服务器套接字;

    构造方法：
        ServerSocket(int port) 创建绑定到指定端口的服务器套接字。

    服务端必须明确一件事情，必须知道是哪个客户端请求的服务器；
    所以可以使用accept方法获取到请求的客户端对象Socket
    成员方法：
            Socket accept() 侦听要连接到此套接字并接受它。

    服务器的实现步骤：
    1、创建服务器SeverSocket对象和系统要指定的端口号；
    2、使用ServerSocket对象中的accept方法，获取到请求客户端对象Socket;
    3、使用Socket对象中的方法getInputStream获取网络输入字节流Inputstream对象；
    4、使用网络字节输入流Inputstream中的方法read，读取客户端发送的数据；
    5、使用Socket对象中的方法getOutputStream（）方法获取网络字节输出流Outputstream对象；
    6、使用网络字节输出流Outputstream对象中的方法write 方法，给客户端回写数据；
    7、释放资源（Socket,SeverSocket）;
 */


public class TcpSever {
    public static void main(String[] args) throws IOException {
        //1、创建服务器SeverSocket对象和系统要指定的端口号；
        ServerSocket serve=new ServerSocket(8888);
        //2、使用ServerSocket对象中的accept方法，获取到请求客户端对象Socket;
        Socket socket=serve.accept();
        //3、使用Socket对象中的方法getInputStream获取网络输入字节流Inputstream对象；
        InputStream is=socket.getInputStream();
        //4、使用网络字节输入流Inputstream中的方法read，读取客户端发送的数据；
        byte[] bytes =new byte[1024];
        int len =is.read(bytes);
        System.out.println(new String(bytes,0,len));
        // 5、使用Socket对象中的方法getOutputStream（）方法获取网络字节输出流Outputstream对象；
        OutputStream os=socket.getOutputStream();
        // 6、使用网络字节输出流Outputstream对象中的方法write 方法，给客户端回写数据；
        os.write("这是收到后的反馈".getBytes());
        //7、释放资源（Socket,SeverSocket）;
        socket.close();
        serve.close();
    }
}
