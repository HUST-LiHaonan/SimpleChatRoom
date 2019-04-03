/**
 * Copyright (C), 2016-2019, 李浩楠
 * FileName: Register
 * Author:   mac
 * Date:     2019-02-26 14:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package client;

import common.BaseDao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author mac
 * @create 2019-02-26
 * @since 1.0.0
 */
public class Register extends JFrame {

    private JPanel contentPane;
    //用户名输入栏
    private JTextField nameField;
    //密码输入栏
    private JPasswordField passwordField;

    /**
     * Create the frame.
     */
    public Register() {
        setTitle("注册");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setVisible(true);
        setResizable(false);


        JLabel nameLabel = new JLabel("用户名");
        nameLabel.setBounds(59, 76, 72, 18);
        contentPane.add(nameLabel);

        JLabel passwordLabel = new JLabel("密码");
        passwordLabel.setBounds(59, 125, 72, 18);
        contentPane.add(passwordLabel);

        nameField = new JTextField();
        nameField.setBounds(145, 73, 159, 24);
        contentPane.add(nameField);
        nameField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(145, 122, 159, 24);
        contentPane.add(passwordField);

        JButton registerButton = new JButton("注册");
        registerButton.setBounds(55, 175, 113, 27);
        /**
         * 进行注册操作
         */
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                int cnt = BaseDao.register(name, password);
                if (cnt == -1) {
                    JOptionPane.showMessageDialog(null, "用户名已存在！");
                } else {
                    JOptionPane.showMessageDialog(null, "注册成功！");
                }
            }
        });
        contentPane.add(registerButton);

        JButton returnButton = new JButton("返回");
        returnButton.setBounds(233, 175, 113, 27);
        /**
         * 返回登录页面
         */
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                login.setVisible(true);
                setVisible(false);
            }
        });
        contentPane.add(returnButton);


    }
}

