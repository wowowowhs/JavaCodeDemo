package com.whs.selector.chatdemo1;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

//参考：https://juejin.cn/post/7032547413764079630
public class NioChatServer {

    private static Map<String, SocketChannel> clientMap = new HashMap<>();//记录客户端信息，方便内容分发

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));
        Selector selector = Selector.open();
        /*
        当accept触发时，就可以触发对应的事件逻辑，
        是将channel绑定到selector ，注册会有 SelectionKey生成
         */
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//一般以连接事件为起源
        while (true) {
            selector.select();//阻塞，等待事件发生
            Set<SelectionKey> selectionKeys = selector.selectedKeys();//返回已发生的注册事件
            selectionKeys.forEach(key -> {//判断事件类型，进行相应操作
                final SocketChannel client;
                try {
                    if (key.isAcceptable()) {//根据key获得channel
                        //之所以转换ServerSocketChannel，因为前面注册的就是这个类
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        client = serverChannel.accept();//新的channel 和客户端建立了通道
                        client.configureBlocking(false);//非阻塞
                        client.register(selector, SelectionKey.OP_READ);//将新的channel和selector，绑定
                        String clientKey = "【" + UUID.randomUUID() + "】";//用UUID，标识客户端client
                        clientMap.put(clientKey, client);
                        //完成客户端注册
                    } else if (key.isReadable()) {//是否有数据可读
                        client = (SocketChannel) key.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        int count = client.read(readBuffer);
                        if (count > 0) {
                            readBuffer.flip();
                            Charset charset = StandardCharsets.UTF_8;
                            String receiveMassage = String.valueOf(charset.decode(readBuffer).array());
                            System.out.println(client + ": " + receiveMassage);//显示哪个client发消息
                            String senderKey = null;
                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                if (client == entry.getValue()) {
                                    senderKey = entry.getKey();//确定哪个client发送的消息
                                    break;
                                }
                            }
                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                SocketChannel channel = entry.getValue();
                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                writeBuffer.put((senderKey + ": " + receiveMassage).getBytes());//告诉所有client ，谁发了消息，发了什么
                                writeBuffer.flip();
                                channel.write(writeBuffer);
                            }
                        }
                    }
                    //selectionKeys.clear();//处理完事件一定要移除
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    selectionKeys.clear();//处理完事件一定要移除
                }
            });
        }
    }

}
