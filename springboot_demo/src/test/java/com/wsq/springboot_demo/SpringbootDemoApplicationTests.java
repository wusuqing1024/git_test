package com.wsq.springboot_demo;

import com.wsq.springboot_demo.pojo.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

@SpringBootTest
class SpringbootDemoApplicationTests {

    @Test
    void contextLoads() {
        Student student = new Student("wsq",21);
        System.out.println(student);
    }

//    TCP协议

    @Test
    void socketTest() throws IOException {
//        创建一个TCP客户端
        Socket socket = new Socket("127.0.0.1",8998);
        System.out.println("成功连接服务器："+ socket);

//        数据传输IO流
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

//        数据传输
        outputStream.write("你好服务器，我是客户端".getBytes());
        System.out.println("发送成功");

//        接受数据
        byte[] bytes = new byte[1024];
        int len = inputStream.read(bytes);
        System.out.println("接受数据：" + new String(bytes,0,len));

//        关闭资源
        outputStream.close();
        inputStream.close();
        socket.close();
    }

    @Test
    void serverSocketTest() throws IOException {
//        启动服务器
        ServerSocket serverSocket = new ServerSocket(8998);
        System.out.println("服务器启动成功");

//        等待客户端连接
        System.out.println("等待客户端连接。。。");
        Socket socket = serverSocket.accept();
        System.out.println("有客户端连接：" + socket);

//        输入输出流
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

//        接受数据
        byte[] bytes = new byte[1024];
//        直到数据传输完成之前，线程都会卡在这个read()上
        int len = inputStream.read(bytes);
        System.out.println("客户端发来数据：" + new String(bytes,0,len));

//        发送数据
        outputStream.write("成功接受到客户端发来的数据".getBytes());

        outputStream.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
    }



//    UDP协议
//    服务器
    @Test
    void udpServer() throws IOException {
        byte [] bytes = new byte[1024];

        DatagramSocket socket = null;
        DatagramPacket packet = null;

//        监听指定端口
        socket = new DatagramSocket(8998);
//        packet用于接受数据
        packet = new DatagramPacket(bytes,0,bytes.length);

        System.out.println("服务器启动成功，等待客户端发送数据");

//        recive方法阻塞，等待客户端发来数据
        socket.receive(packet);

        System.out.println("客户端发来数据：" + new String(packet.getData(),0,packet.getLength()));

//        关闭资源
        socket.close();
    }

//    客户端
    @Test
    void udpClient() throws IOException {
        byte [] bytes = "你好呀啊啊啊啊！！！！".getBytes();
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(bytes,bytes.length,InetAddress.getByName("127.0.0.1"),8998);

//        发送数据
        socket.send(packet);
        System.out.println("发送成功");

//        关闭资源
        socket.close();
    }


//    NIO编程
    @Test
    void nioServer() throws IOException {
//        selector
//        1、多路复用，一个selector可以处理多个client请求
//        2、事件监听，accept(发起连接)、connect(连接成功)、read、write
        Selector selector = Selector.open();

//        serverSocketChannel
//        1、监听端口，接受请求，作用与serverScoket一样
//        2、可设置阻塞和非阻塞模式，针对服务器IO事件
//        3、支持selector
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);   //非阻塞模式
        serverSocketChannel.socket().bind(new InetSocketAddress(8998));  //绑定端口

//        serverScoketChannel注册到seletor，selector来监听accept事件--->发起连接
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("NIO服务器启动，开始监听端口8998");


        while (true) {
               selector.select();
               Set<SelectionKey> keys = selector.selectedKeys();
               Iterator<SelectionKey> iterator = keys.iterator();

               while (iterator.hasNext()) {
                   SelectionKey key = iterator.next();
                   iterator.remove(); // 使用Iterator移除已处理的key

                   if (key.isValid()) {
                       if (key.isAcceptable()) {
                           handelAccept(key);
                       } else if (key.isReadable()) {
                           handelRead(key);
                       }
                   }
               }
           }

    }

    // 客户端向服务器发起连接请求
    void handelAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);  //关闭阻塞模式NIO

//        将socketChannel注册到selector
        socketChannel.register(key.selector(), SelectionKey.OP_READ);


        System.out.println("客户端连接成功: " + socketChannel);

//        发送成功信息回去
        socketChannel.write(ByteBuffer.wrap("成功连接上服务器".getBytes()));
    }

     void handelRead(SelectionKey key) throws IOException {
         SocketChannel socketChannel = (SocketChannel) key.channel();
         ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

         try {
             int len;
             while ((len = socketChannel.read(byteBuffer)) > 0) {
                 byteBuffer.flip();
                 System.out.println("客户端发来数据：" +
                     new String(byteBuffer.array(), 0, byteBuffer.remaining()));
                 byteBuffer.clear(); // 清空缓冲区以备下次读取
             }
         } catch (IOException e) {
             System.out.println("客户端异常断开");
             socketChannel.close();
             key.cancel();
         }
     }



//    NIO客户端
    @Test
    void nioClient() throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8998));

        // 等待连接完成
        while (!socketChannel.finishConnect()) {
            Thread.sleep(10);
        }
        System.out.println("连接成功");

        // 读取服务器响应
        ByteBuffer receiver = ByteBuffer.allocate(1024);
        int totalRead = 0;
        while (totalRead == 0) {
            int len = socketChannel.read(receiver);
            if (len > 0) {
                receiver.flip();
                System.out.println("服务器发来数据：" +
                    new String(receiver.array(), 0, receiver.remaining()));
                totalRead += len;
            }
        }

        // 发送数据
        ByteBuffer byteBuffer = ByteBuffer.wrap("你好呀，服务器!!!".getBytes());
        while (byteBuffer.hasRemaining()) {
            socketChannel.write(byteBuffer);
        }

        socketChannel.close();
    }
    
}
