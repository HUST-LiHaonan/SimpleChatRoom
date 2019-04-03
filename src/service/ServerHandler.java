/**
 * Copyright (C), 2016-2019, 李浩楠
 * FileName: ServerHandler
 * Author:   mac
 * Date:     2019-02-26 16:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package service;

import common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author mac
 * @create 2019-02-26
 * @since 1.0.0
 */
public class ServerHandler implements Runnable {
    // 输入流
    private ObjectInputStream inputStream;
    // 输出流
    private ObjectOutputStream outputStreamt;
    // 用户名
    private String name;
    //将用户线程放到一个链表里
    private static LinkedList <ServerHandler> clientList = new LinkedList <>();
    //将用户名和自己的线程直接组成键值对，相互关联,建立路由表
    private static HashMap <String, ServerHandler> clientTab = new HashMap <>();

    public ServerHandler(Socket socket) {
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStreamt = new ObjectOutputStream(socket.getOutputStream());
            System.out.println(socket + "客户端已建立连接");
        } catch (IOException e) {
            System.out.println(e.toString() + " 与服务器连接失败");
        }
    }

    public void run() {
        try {
            // 客户端经过登录检验 添加到列表
            if (!loginValid()) {
                //若未通过登录验证，则通知已经在线的客户端有别处登录
                sendMessage(new Message("otherLogin", "serverHandler", clientTab.get(name).name, "你已在其他地方登录！"));
                return;
            }
            //添加用户到线程列表
            addClient();
            // 广播用户列表
            sendMessage(new Message("online", name, "全体成员", "上线了!"));
            // 循环等待接受客户端发来的的消息
            while (true) {
                Message msg = (Message) inputStream.readObject();
                sendMessage(msg);
            }
        } catch (Exception e) {
            System.out.println(e.toString() + " " + name + "的客户端已下线");
        } finally {
            //关闭客户端
            removeClient();
            sendMessage(new Message("offline", name, "全体成员", "下线了！")); // 广播下线通知
        }
    }

    /**
     * 登录校验
     */
    private Boolean loginValid() {
        try {
            Message msg = (Message) inputStream.readObject();
            System.out.println(msg.getSender() + "的客户端正在登陆！");
            this.name = msg.getSender();
            //当前客户端用户已经登录
            if (clientTab.containsKey(name)) {
                sendToClient(this, new Message("loginError", "serverHandler", name, "该用户已登录!"));
                System.out.println(msg.getSender() + "的客户端已登录");
                return false;
            }
            System.out.println(msg.getSender() + "的客户端登录成功");
        } catch (Exception e) {
            System.out.println(e.toString() + "登录失败");
            return false;
        }
        return true;
    }

    /**
     * 添加用户到列表
     */
    private Boolean addClient() {
        if (clientTab.containsKey(name))
            return false;
        clientList.add(this);
        clientTab.put(name, this);
        return true;
    }

    /**
     * 移除当前用户
     */
    private Boolean removeClient() {
        clientList.remove(this);
        clientTab.remove(name);
        return true;
    }

    /**
     * 发送消息
     *
     * @param msg
     * @throws IOException
     */
    public void sendMessage(Message msg) {
        new ServerMsgHandler(this, msg).process();
    }

    /**
     * 广播，发送消息给所有用户,排除当前线程
     *
     * @param msg
     */
    public void broadcast(Message msg) {
        String name = msg.getSender();
        ServerHandler serverHandler = clientTab.get(name);
        //对线程列表进行加锁处理,避免死锁发送
        synchronized (clientList) {
            for (ServerHandler client : clientList) {
                if (client != serverHandler) {
                    sendToClient(client, msg);
                }
            }
        }
    }

    /**
     * 发送消息给指定用户线程
     *
     * @param msg
     */
    public void sendToClient(Message msg) {
        String name = msg.getReceiver();
        ServerHandler client = clientTab.get(name);
        sendToClient(client, msg);
    }

    private void sendToClient(ServerHandler client, Message msg) {
        try {
            client.outputStreamt.writeObject(msg);
            client.outputStreamt.flush();
        } catch (IOException e) {
            System.out.print(e.toString() + " 服务端发送消息失败");
        }
    }

    /**
     * 更新用户列表
     * 线程加锁，进行同步操作
     *
     * @param msg
     */
    public synchronized void sendClientList(Message msg) {
        // 新上线的用户
        ServerHandler newclient = clientTab.get(msg.getSender());
        for (ServerHandler client : clientList) {
            if (client == newclient)
                continue;
            // 给新登录用户提供在线列表
            sendToClient(newclient, new Message(msg.getType(), client.name, newclient.name, null));
            // 更新在线用户
            sendToClient(client, new Message(msg.getType(), newclient.name, client.name, msg.getText()));
        }
    }

}

