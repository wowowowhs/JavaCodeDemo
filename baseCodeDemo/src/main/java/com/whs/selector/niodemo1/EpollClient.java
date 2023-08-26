package com.whs.selector.niodemo1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class EpollClient {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8000));

            ByteBuffer writeBuffer = ByteBuffer.allocate(32);
            ByteBuffer readBuffer = ByteBuffer.allocate(32);

//            writeBuffer.put("hello".getBytes());
//            writeBuffer.flip();
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("请输入：");
                String msg = sc.nextLine();
                writeBuffer.put(msg.getBytes());
                writeBuffer.flip();
                writeBuffer.rewind();
                socketChannel.write(writeBuffer);
                readBuffer.clear();
                socketChannel.read(readBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
