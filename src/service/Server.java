/**
 * Copyright (C), 2016-2019, 李浩楠
 * FileName: Server
 * Author:   mac
 * Date:     2019-02-26 16:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package service;

import common.ipConfig;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author mac
 * @create 2019-02-26
 * @since 1.0.0
 */
public class Server {
    public Server(int port) {
        try {
            @SuppressWarnings("Resource")
            ServerSocket server = new ServerSocket(port);
            System.out.println("服务器启动");
            while (true) {
                //建立套接字
                System.out.println("正在等待连接");
                Socket socket = server.accept();
                System.out.println(socket+" 连接成功");
                // 建立与相应客户端匹配的服务器端线程
                new Thread(new ServerHandler(socket)).start();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        new Server(ipConfig.PORT);
    }
}

