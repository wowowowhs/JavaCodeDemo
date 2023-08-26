package com.whs.selector.niodemo2;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Client {

    public static void main(String[] args) throws Exception {
        //1. 获取通道，绑定主机和端口号
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(20000));

        //2. 切换到非阻塞模式
        socketChannel.configureBlocking(false);

        //3. 创建 buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //4. 写入 buffer 数据
        buffer.put(new Date().toString().getBytes(StandardCharsets.UTF_8));

        //5. 模式切换
        buffer.flip();

        //6. 写入通道
        socketChannel.write(buffer);

        //7. 关闭
        buffer.clear();
        socketChannel.close();

    }

}
