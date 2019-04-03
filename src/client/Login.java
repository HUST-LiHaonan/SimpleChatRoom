/**
 * Copyright (C), 2016-2019, 李浩楠
 * FileName: Login
 * Author:   mac
 * Date:     2019-02-26 13:55
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package client;

import common.BaseDao;
import common.ipConfig;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author mac
 * @create 2019-02-26
 * @since 1.0.0
 */
public class Login extends JFrame {
    //设置初始画板
    private JPanel contentPane;
    //姓名提示
    private JLabel nameLabel;
    //密码提示
    private JLabel passwordLabel;
    //用户名输入栏
    private JTextField nameField;
    //密码输入栏
    private JPasswordField passwordField;
    //登录按钮
    private JButton loginButton;
    //注册按钮
    private JButton registerButton;
    //数据库接口
    private Connection conn = BaseDao.getConn();


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Login() {
        setTitle("登录");
        //退出关闭窗口
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        //自有布局
        contentPane.setLayout(null);
        setVisible(true);
        setResizable(false);

        nameLabel = new JLabel("用户名");
        nameLabel.setBounds(85, 82, 72, 18);
        contentPane.add(nameLabel);

        passwordLabel = new JLabel("密码");
        passwordLabel.setBounds(85, 126, 72, 18);
        contentPane.add(passwordLabel);

        nameField = new JTextField();
        nameField.setBounds(171, 79, 145, 24);
        contentPane.add(nameField);
        nameField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(171, 126, 145, 24);
        contentPane.add(passwordField);

        loginButton = new JButton("登录");
        loginButton.setBounds(85, 189, 72, 40);
        contentPane.add(loginButton);
        //登录事件监听器
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                //登录逻辑判断
                if (!BaseDao.hasName(name)) {
                    JOptionPane.showMessageDialog(null, "用户名不存在");
                    return;
                } else if (password.equals(BaseDao.getPwd(name))) {
                    new ClientHandler(ipConfig.IP, ipConfig.PORT, name);
                    setVisible(false);
                    return;
                }
                if (BaseDao.hasName(name)) {
                    JOptionPane.showMessageDialog(null, "密码错误");
                    return;
                }
            }
        });


        registerButton = new JButton("注册");
        registerButton.setBounds(211, 189, 79, 40);
        contentPane.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Register register = new Register();
                setVisible(false);
            }
        });


    }
}

