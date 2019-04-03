/**
 * Copyright (C), 2016-2019, 李浩楠
 * FileName: Message
 * Author:   mac
 * Date:     2019-02-26 12:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br消息实体>
 * 〈Message实现Serializable接口的意义在于使得Message类可以序列化，在客户端和服务器端是通过套接字来传递信息的
 *  所以要实现序列化〉
 *
 * @author mac
 * @create 2019-02-26
 * @since 1.0.0
 */
public class Message implements Serializable{


    private static final long serialVersionUID = -8104648599710335924L;

    // 消息类型,默认传递字符串消息
    private String type = "chat";
    // 发送者
    private String sender;
    // 接受者
    private String receiver;
    // 消息内容
    private String text;


    public Message(String type, String sender, String receiver, String text) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
    }

    public String toString() {
        return "type=" + type + " sender=" + sender + " receiver=" + receiver + " text=" + text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}

