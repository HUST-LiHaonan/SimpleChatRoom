/**
 * Copyright (C), 2016-2019, 李浩楠
 * FileName: BaseDao
 * Author:   mac
 * Date:     2019-02-26 14:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common;

import java.sql.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author mac
 * @create 2019-02-26
 * @since 1.0.0
 */
public class BaseDao {
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/chatroom?characterEncoding=UTF-8&useSSL=false";
    private static String user = "root";
    private static String pwd = "123456";
    private static Connection conn = null;
    private static PreparedStatement stm;

    /**
     * 获取数据库接口
     */
    public static Connection getConn() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return conn;
    }

    /**
     * 获取密码
     *
     * @param name
     */
    public static String getPwd(String name) {
        Connection connection = getConn();
        String sql = "select password from user where name=?";
        try {
            stm = conn.prepareStatement(sql);
            stm.setString(1, name);
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                return res.getString("password");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    /**
     * 判断用户名是否已经注册
     *
     * @param name
     */
    public static boolean hasName(String name) {
        Connection connection = getConn();
        String sql = "select * from user where name=?";
        try {
            stm = conn.prepareStatement(sql);
            stm.setString(1, name);
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }

    /**
     * 用户注册
     *
     * @param name,password
     */
    public static int register(String name, String password) {
        Connection connection = getConn();
        if (hasName(name)) {
            return -1;
        }
        String sql = "insert into user values(null ,?,?)";
        try {
            stm = conn.prepareStatement(sql);
            stm.setString(1, name);
            stm.setString(2, password);
            int cnt = stm.executeUpdate();
            if (cnt > 0) {
                return cnt;
            } else {
                return -1;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return -1;
    }
}

