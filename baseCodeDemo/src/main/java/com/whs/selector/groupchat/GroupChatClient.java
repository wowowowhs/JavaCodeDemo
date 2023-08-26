package com.whs.selector.groupchat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class GroupChatClient {
    //定义相关的属性
    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    //构造器,完成初始化工作
    public GroupChatClient() throws IOException {
        selector = Selector.open();
        socketChannel = socketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        //将channel注册到selector、
        socketChannel.register(selector, SelectionKey.OP_READ);
        //得到用户名
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " is ready...");
    }

    //向服务器发送消息
    public void sendInfo(String info) {

        info = username + "说" + info;

        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //从服务器端读取消息
    public void readInfo() {
        try {
            int readChannels = selector.select();
            if (readChannels > 0) {
                //有可用的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();

                    if (key.isReadable()) {
                        //得到相关的通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        //得到一个buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取
                        sc.read(buffer);
                        //把读取到的缓冲区数据转成字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                }

                iterator.remove(); //删除当前的selectionKey，防止重复操作
            } else {
                //System.out.println("没有可用的通道");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        //启动客户端
        GroupChatClient chatClient = new GroupChatClient();

        //启动一个线程,每隔3秒，读取从服务器端发送的数据
        new Thread() {
            public void run() {
                while (true) {
                    chatClient.readInfo();

                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //发送数据给服务器
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入：");

        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
            System.out.println("请输入：");
        }

    }

}