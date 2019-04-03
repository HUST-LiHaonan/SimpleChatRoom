/**
 * Copyright (C), 2016-2019, 李浩楠
 * FileName: ClientMsgHandler
 * Author:   mac
 * Date:     2019-02-26 14:31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package client;

import common.Message;

import javax.swing.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author mac
 * @create 2019-02-26
 * @since 1.0.0
 */
public class ClientMsgHandler {
    ClientHandler clientHandler;
    Message msg;

    public ClientMsgHandler(ClientHandler clientHandler, Message msg) {
        this.clientHandler = clientHandler;
        this.msg = msg;
    }

    /**
     * 处理接收到的消息
     */
    public void process() {
        try {
            String msgType = msg.getType();

            if(msgType.equals("chat")){
                chat();
            }
            if(msgType.equals("online")){
                online();
            }
            if(msgType.equals("offline")){
                offline();
            }
            if(msgType.equals("loginError")){
                loginError();
            }
            if(msgType.equals("otherLogin")){
                otherLogin();
            }
            //动态更新在线人数
            clientHandler.client.setCount("在线人数:" + (clientHandler.client.getClientCount()));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * 聊天消息处理
     */
    public void chat() {
        String toWho = "[公共]";
        if (msg.getReceiver().equals(clientHandler.getClientName())) {
            toWho = "[私信]";
        }
        clientHandler.client.write(toWho + msg.getSender() + ": " + msg.getText() + "\n");
    }

    /**
     * 在线提醒处理
     */
    public void online() {
        clientHandler.client.addClient(msg.getSender());
        if (msg.getText() != null)
            clientHandler.client.write(msg.getSender() + msg.getText() + "\n");
    }

    /**
     * 下线提醒处理
     */
    public void offline() {
        clientHandler.client.removeClient(msg.getSender());
        if (msg.getText() != null)
            clientHandler.client.write(msg.getSender() + msg.getText() + "\n");
    }

    /**
     * 登录出错处理
     */
    public void loginError() {
        clientHandler.client.setVisible(false);
        JOptionPane.showMessageDialog(clientHandler.client, msg.getText());
        System.out.println(msg.getText());
        clientHandler.closeResource();
        new Login();
    }

    /**
     * 异地登录处理
     */
    public void otherLogin(){
        clientHandler.client.setVisible(false);
        JOptionPane.showMessageDialog(clientHandler.client, msg.getText());
        System.out.println(msg.getText());
        clientHandler.closeResource();
        new Login();
    }
}

