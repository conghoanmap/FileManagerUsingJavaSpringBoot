package com.javaconnectoracle.filemanager.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;

@Service
public class ConnectionService {
    public static Connection Connect(String username, String password) {
        try {
            Connection connection = null;
            String url = "jdbc:oracle:thin:@//localhost:1521/orcl2";
            Class.forName("oracle.jdbc.driver.OracleDriver");
            username = username.toUpperCase();
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalArgumentException("Tài khoản hoặc mật khẩu không đúng hoặc đang bị khóa!!!");
        }
    }

    public static Connection getConnection(HttpSession session) {
        Connection connection = (Connection) session.getAttribute("conn");
        return connection;
    }

    public static boolean Disconnect(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Logout failure: " + e.getMessage());
            return false;
        }
        return false;
    }
}
