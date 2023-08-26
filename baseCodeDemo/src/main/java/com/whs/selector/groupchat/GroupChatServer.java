package com.whs.selector.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

//参考：https://blog.csdn.net/feiku_ysu/article/details/107220012?spm=1001.2101.3001.6650.11&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-11-107220012-blog-103882074.235%5Ev38%5Epc_relevant_sort_base1&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-11-107220012-blog-103882074.235%5Ev38%5Epc_relevant_sort_base1&utm_relevant_index=12
public class GroupChatServer {
    //定义相关的属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    //构造器 初始化操作
    public GroupChatServer() {
        try {
            //得到选择器
            selector = Selector.open();
            //初始化ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞
            listenChannel.configureBlocking(false);
            //将该listenChannel注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //监听
    public void listen() {
        try {
            //循环处理
            while (true) {

                int count = selector.select();
                if (count > 0) {   //如果有事件则处理
                    //遍历得到的selectorKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //取出SelectionKey
                        SelectionKey key = iterator.next();

                        //监听到ACCEPT事件
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            //将该sc注册到selector上
                            sc.register(selector, SelectionKey.OP_READ);

                            //提示上线了
                            System.out.println(sc.getRemoteAddress() + "上线了。。。");

                        }
                        if (key.isReadable()) {
                            //通道发生read事件
                            //专门处理读数据的方法
                            readData(key);

                        }

                        //将当前的key删除，防止重复处理
                        iterator.remove();

                    }


                } else {
                    System.out.println("等待....");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            //发生的异常处理
        }
    }

    //读取客户端消息
    private void readData(SelectionKey key) {

        //定义一个SocketChannel
        SocketChannel channel = null;
        try {
            //取到关联的channel
            channel = (SocketChannel) key.channel();
            //创建缓冲buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            int count = channel.read(byteBuffer);

            //根据count的值做处理
            if (count > 0) {
                //把缓冲区的数据转换成字符串
                String msg = new String(byteBuffer.array());

                //输出该消息
                System.out.println("from 客户端：" + msg);

                //向其他客户端转发消息，专门写一个方法处理
                sendInfoToOthers(msg, channel);
            }

        } catch (IOException e) {
            //e.printStackTrace();

            try {
                System.out.println(channel.getRemoteAddress() + "已下线");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (Exception r) {
                r.printStackTrace();
            }
        }
    }

    //转发消息给其他的客户，实际是转发给其他通道************需要排除自身
    private void sendInfoToOthers(String msg, SocketChannel self) throws IOException {

        //服务器转发消息
        System.out.println("服务器转发消息中。。。");
        //遍历所有注册到selector的socketchannel并排除自身
        for (SelectionKey key : selector.keys()) {

            //反向获取通道
            Channel targetchannel = key.channel();

            //排除自身
            if (targetchannel instanceof SocketChannel && targetchannel != self) {

                //转型
                SocketChannel dest = (SocketChannel) targetchannel;

                //将msg存储到buffer中
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

                //将buffer中的数据写入通道
                dest.write(buffer);
            }
        }

    }

    public static void main(String[] args) {

        //创建一个服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();

        //监听
        groupChatServer.listen();
    }
}
