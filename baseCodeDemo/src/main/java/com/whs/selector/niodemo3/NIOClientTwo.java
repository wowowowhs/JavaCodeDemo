package com.whs.selector.niodemo3;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class NIOClientTwo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (
                SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8888));
        ) {
            Charset charset = Charset.forName("utf8");
            while (true) {
                System.out.print("客户端2请输入：");
                String str = scanner.nextLine();
                socketChannel.write(charset.encode(str));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
