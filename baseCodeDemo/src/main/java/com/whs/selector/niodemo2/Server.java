package com.whs.selector.niodemo2;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

//参考：https://juejin.cn/post/7059400681949495327
public class Server {

    public static void main(String[] args) throws Exception {
        //1. 获取服务端通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //2. 切换非阻塞模式
        serverSocketChannel.configureBlocking(false);

        //3. 创建 buffer
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        writeBuffer.put("收到了。。。。".getBytes(StandardCharsets.UTF_8));

        //4. 绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(20000));

        //5. 获取 selector 选择器
        Selector selector = Selector.open();

        //6. 通道注册到选择器，进行监听
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //7. 选择器进行轮训，进行后续操作
        while (selector.select() > 0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
            // 循环
            while (selectionKeyIterator.hasNext()) {
                // 获取就绪状态
                SelectionKey k = selectionKeyIterator.next();
                selectionKeyIterator.remove();
                // 操作判断
                if (k.isAcceptable()) {
                    // 获取连接
                    SocketChannel accept = serverSocketChannel.accept();

                    // 切换非阻塞模式
                    accept.configureBlocking(false);

                    // 注册
                    accept.register(selector, SelectionKey.OP_READ);
                } else if (k.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) k.channel();
                    readBuffer.clear();
                    socketChannel.read(readBuffer);

                    readBuffer.flip();
                    System.out.println("received:" + new String(readBuffer.array(), StandardCharsets.UTF_8));
                    k.interestOps(SelectionKey.OP_WRITE);
                } else if (k.isWritable()) {
                    writeBuffer.rewind();

                    SocketChannel socketChannel = (SocketChannel) k.channel();
                    socketChannel.write(writeBuffer);
                    k.interestOps(SelectionKey.OP_READ);
                }
            }
        }
    }
}
/**
 * NIO编程步骤总结
 * 1、创建一个 ServerSocketChannel 通道
 * 2、设置为非阻塞模式
 * 3、创建一个 Selector 选择器
 * 4、Channel 注册到选择器中，监听连接事件
 * 5、调用 Selector 中的 select 方法（循环调用），监听通道是否是就绪状态
 * 6、调用 SelectKeys() 方法就能获取 就绪 channel 集合
 * 7、遍历就绪的 channel 集合，判断就绪事件类型，实现具体的业务操作。
 * 8、根据业务流程，判断是否需要再次注册事件监听事件，重复执行。
 */
