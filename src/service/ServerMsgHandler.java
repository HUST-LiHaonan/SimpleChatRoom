/**
 * Copyright (C), 2016-2019, 李浩楠
 * FileName: ServerMsgHandler
 * Author:   mac
 * Date:     2019-02-26 16:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package service;

import common.Message;


/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author mac
 * @create 2019-02-26
 * @since 1.0.0
 */
public class ServerMsgHandler {
    ServerHandler serverHandler;
    Message msg;

    public ServerMsgHandler(ServerHandler serverHandler, Message msg) {
        this.serverHandler = serverHandler;
        this.msg = msg;
    }

    public void process() {
        try {
            String msgType = msg.getType();
            if(msgType.equals("chat")){
                chat();
            }else if(msgType.equals("online")){
                online();
            }else if (msgType.equals("offline")){
                offline();
            }else {
                chat();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * 会话消息处理
     */
    public void chat() {
        if (msg.getReceiver().equals("全体成员")) {
            // 给所有人发送消息（广播）
            serverHandler.broadcast(msg);
        } else {
            // 发送消息给指定的人（私信）
            serverHandler.sendToClient(msg);
        }
    }

    /**
     * 上线消息处理
     */
    public void online() {
        serverHandler.sendClientList(msg);
    }


    /**
     * 下线消息处理
     */
    public void offline() {
        serverHandler.broadcast(msg);
    }
}

