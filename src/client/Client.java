/**
 * Copyright (C), 2016-2019, 李浩楠
 * FileName: Client
 * Author:   mac
 * Date:     2019-02-26 15:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package client;

import common.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author mac
 * @create 2019-02-26
 * @since 1.0.0
 */
public class Client extends Frame {
    // 显示区
    private JTextArea messageArea;
    // 输入区
    private JTextField messageField;
    // 在线列表
    private JComboBox <String> chatItem;
    // 发送消息按钮
    private JButton sendButton;
    //清屏按钮
    private JButton clearButton;
    // 统计在线人数
    private JLabel countLabel;
    //在线列表
    private JLabel onlineLabel;
    //信息显示画板
    private JScrollPane msgPane;
    //用户列表显示画板
    private JScrollPane userPane;
    //用户在线列表画板
    private JPanel userJPanel;
    //功能区
    private JPanel functionalJPanel;
    //用户列表文字提示
    private JLabel chatLabel;
    //显示在线用户
    private JList userList;
    //在线用户存储
    private Vector <String> userVector;
    //当前窗体对应的客户端后台
    private ClientHandler clientHandler;

    /**
     * Create the frame.
     */
    public Client(ClientHandler clientHandler, String name) {
        //设置客户端标题
        setTitle(name + "的客户端");
        //设置窗体大小
        setSize(600, 400);

        this.clientHandler = clientHandler;
        userJPanel = new JPanel();
        functionalJPanel = new JPanel();

        // 消息文本显示区域
        messageArea = new JTextArea(400, 400);
        messageArea.setEditable(false);
        msgPane = new JScrollPane(messageArea);
        add(msgPane, BorderLayout.CENTER);
        // 发送消息区域
        functionalJPanel.setLayout(new FlowLayout(0, 10, 10));
        add(functionalJPanel, BorderLayout.SOUTH);

        //用户列表区域
        add(userJPanel, BorderLayout.EAST);

        ////设置GridLayout布局
        GridLayout gridLayout = new GridLayout(2, 2, 3, 3);
        userJPanel.setLayout(gridLayout);

        //选择聊天对象提示
        chatLabel = new JLabel("请选择聊天对象");

        // 选择聊天对象
        chatItem = new JComboBox <String>();
        chatItem.addItem("全体成员");

        //获取在线列表
        userVector = new Vector <>();
        userVector.addElement(name);
        userList = new JList();
        userList.setListData(userVector);
        userList.setSize(200, 200);
        userPane = new JScrollPane(userList);

        //在线列表
        onlineLabel = new JLabel("当前在线用户");

        //填充组件
        userJPanel.add(onlineLabel);
        userJPanel.add(userPane);
        userJPanel.add(chatLabel);
        userJPanel.add(chatItem);

        // 输入框
        messageField = new JTextField(20);
        functionalJPanel.add(messageField);

        // 发送按钮
        sendButton = new JButton("发送");
        sendButton.setMnemonic(KeyEvent.VK_ENTER);
        functionalJPanel.add(sendButton);

        //清屏按钮
        clearButton = new JButton("清屏");
        functionalJPanel.add(clearButton);

        // 显示在线人数
        countLabel = new JLabel("当前在线人数:1");
        functionalJPanel.add(countLabel);

        // 发送消息按钮监听器
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = getText().trim();
                if (content.equals("")) {
                    JOptionPane.showMessageDialog(clientHandler.client, "输入不能为空");
                    return;
                }
                write("你对 " + getChatName() + "说: " + content + "\n");
                //消息默认设置为chat类型
                clientHandler.send(new Message("chat", clientHandler.getClientName(), getChatName(), content));
                //发送消息后清空输入框
                clear();
            }
        });

        //清屏按钮监听器
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageArea.setText("");
            }
        });

        //退出确认提示
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(clientHandler.client, "确定退出吗?")) {
                    clientHandler.closeResource();
                    System.exit(0);
                }
            }
        });

        //设置窗体可见
        setVisible(true);
    }

    /**
     * 获得输入框中的消息文本
     */
    public String getText() {
        return messageField.getText().trim();
    }

    /**
     * 设置cntLabel文本以更新在线用户数
     *
     * @param text
     */
    public void setCount(String count) {
        countLabel.setText(count);
    }

    /**
     * 获得选中的聊天对象的名字
     */
    public String getChatName() {
        return (String) chatItem.getSelectedItem();
    }

    /**
     * 获得在线用户数
     */
    public int getClientCount() {
        return chatItem.getItemCount();
    }

    /**
     * 添加在线用户
     *
     * @param name 用户名
     */
    public void addClient(String name) {
        chatItem.addItem(name);
        userVector.addElement(name);
        userList.setListData(userVector);
    }

    /**
     * 删除在线用户
     *
     * @param name 用户名
     */
    public void removeClient(String name) {
        chatItem.removeItem(name);
        userVector.removeElement(name);
        userList.setListData(userVector);
    }

    /**
     * 清空输入框
     */
    public void clear() {
        messageField.setText(" ");
    }

    /**
     * 添加文本到消息显示区域
     *
     * @param text
     */
    public void write(String text) {
        messageArea.append(text);
    }
}

