/**
 * Copyright (C), 2016-2019, 李浩楠
 * FileName: ClientHandler
 * Author:   mac
 * Date:     2019-02-26 13:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package client;

import common.Message;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author mac
 * @create 2019-02-26
 * @since 1.0.0
 */
public class ClientHandler extends Thread {
    // 客户端界面
    public Client client;
    // 当前线程名称
    private String name;
    // 客户端套接字
    private Socket clientSocket;
    // 输入流
    private ObjectInputStream inputStream;
    // 输出流
    private ObjectOutputStream outputStream;

    /**
     * 登录阶段初始化客户端线程
     */
    public ClientHandler(String server, int port, String name) {
        try {
            clientSocket = new Socket(server, port);
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            this.name = name;
        } catch (Exception e) {
            System.out.println(e.toString() + " 客户端初始化失败");
            JOptionPane.showMessageDialog(null, "客户端连接失败,请检查服务器！");
            //关闭资源
            closeResource();
            return;
        }
        //启动线程
        start();
    }

    public void run() {
        try {
            // 初始化后，向服务器发送登录信息
            send(new Message("login", name, null, null));
            // 显示主界面
            client = new Client(this, name);
            // 消息处理主循环
            while (true) {
                // 服务器端发来的消息
                Message msg = (Message) inputStream.readObject();
                System.out.println(msg.toString());
                //处理接收到的消息
                new ClientMsgHandler(this, msg).process();
            }
        } catch (Exception e) {
            System.out.println(e.toString() + " 连接丢失");
            //关闭资源
            closeResource();
            return;
        }
    }

    /**
     * 客户端发送消息给服务器
     *
     * @param msg
     * 消息对象
     */
    public void send(Message msg) {
        try {
            outputStream.writeObject(msg);
            outputStream.flush();
        } catch (Exception e) {
            System.out.println(e.toString() + " 消息转换出错");
        }
    }

    /**
     * 关闭所有资源
     */
    public void closeResource() {
        try {
            if (clientSocket != null)
                clientSocket.close();
            if (outputStream != null)
                outputStream.close();
            if (inputStream != null)
                inputStream.close();
            if (client != null)
                client.dispose();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public String getClientName() {
        return name;
    }
}


