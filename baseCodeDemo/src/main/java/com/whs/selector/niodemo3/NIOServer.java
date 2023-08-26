package com.whs.selector.niodemo3;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;

//参考：https://zhuanlan.zhihu.com/p/622360037
public class NIOServer {

    public static void main(String[] args) {
        try (
                //创建NIO服务对象
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ) {
            //将该服务对象绑定到某IP地址和端口
            serverSocketChannel.bind(new InetSocketAddress("localhost", 8888));
            //为设置ServerSocket以非阻塞方式工作
            serverSocketChannel.configureBlocking(false);
            //打开多路复用选择器
            Selector selector = Selector.open();
            //将 serverSocketChannel 注册到多路复用选择器
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);   //用于接收数据
            System.out.println("NIO服务器启动！");
            //循环判断多路复用选择器是否接收到数据
            while (selector.select() > 0) {
                //遍历所有活动的key
                for (SelectionKey sk : selector.selectedKeys()) {
                    //活动key立马移除该key
                    selector.selectedKeys().remove(sk);
                    //如果是客户端连接请求
                    if (sk.isAcceptable()) {
                        //接收到客户端的通道
                        Channel channel = serverSocketChannel.accept();
                        //将该通道注册到多路选择复用器
                        SocketChannel socketChannel = (SocketChannel) channel;
                        //设置Socket以非阻塞方式工作
                        socketChannel.configureBlocking(false);
                        //将这个客户端 channel 注册到多路选择复用器上
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println("收到新的连接。");
                    }
                    //如果是数据请求
                    if (sk.isReadable()) {
                        //将频道转为 SockerChannel
                        SocketChannel channel = (SocketChannel) sk.channel();
                        //读取频道的数据
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        buffer.flip(); //重置buffer的光标与limit，为读取数据做准备
                        //创建编码集对象
                        Charset charset = Charset.forName("utf8");
                        CharBuffer charBuffer = charset.decode(buffer);
                        System.out.println("读到的数据为：" + charBuffer);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/**
 * open():创建一个Selector对象
 * isOpen():是否是open状态，如果调用了close()方法则会返回false
 * provider():获取当前Selector的Provider
 * keys():如上文所述，获取当前channel注册在Selector上所有的key
 * selectedKeys():获取当前channel就绪的事件列表
 * selectNow():获取当前是否有事件就绪，该方法立即返回结果，不会阻塞；如果返回值>0，则代表存在一个或多个
 * select(long timeout):selectNow的阻塞超时方法，超时时间内，有事件就绪时才会返回；否则超过时间也会返回
 * select():selectNow的阻塞方法，直到有事件就绪时才会返回
 * wakeup():调用该方法会时，阻塞在select()处的线程会立马返回；(ps：下面一句划重点)即使当前不存在线程阻塞在select()处，那么下一个执行select()方法的线程也会立即返回结果，相当于执行了一次selectNow()方法
 * close(): 用完Selector后调用其close()方法会关闭该Selector，且使注册到该Selector上的所有SelectionKey实例无效。channel本身并不会关闭。
 */
