package com.javaconnectoracle.filemanager.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.javaconnectoracle.filemanager.entity.UserEntity;

@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserEntity getUser(String username, Connection conn) {
        try {
            String sql = "SELECT * FROM adminfilemng.V_USERS WHERE USERNAME = ?";
            // Đọc dữ liệu bằng biến conn
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            UserEntity user = new UserEntity();
            if (rs.next()) {
                user.setUSERNAME(rs.getString("USERNAME"));
                user.setFULLNAME(rs.getString("FULLNAME"));
                user.setEMAIL(rs.getString("EMAIL"));
                user.setPHONE(rs.getString("PHONE"));
            }
            return user;
        } catch (Exception e) {
            throw new IllegalArgumentException("Không tìm thấy thông tin người dùng!!!");
        }
    }

    @SuppressWarnings("null")
    public boolean isCustomer(String username) {
        try {
            String sql = "SELECT DEFAULT_TABLESPACE FROM V_DBA_USERS WHERE USERNAME = UPPER('" + username + "')";
            String defaultTablespace = jdbcTemplate.queryForObject(sql, String.class);
            return defaultTablespace.equals("USERS");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateUser(UserEntity user, Connection conn) {
        String sql = "CALL adminfilemng.update_user_info(?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUSERNAME());
            ps.setString(2, user.getFULLNAME());
            ps.setString(3, user.getEMAIL());
            ps.setString(4, user.getPHONE());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Update User Error: " + e.getMessage());
            return false;
        }
    }

    public long getSizeUser(String Username) {
        try {
            String sql = "SELECT adminfilemng.calculate_used_capacity(?) FROM DUAL";
            return jdbcTemplate.queryForObject(sql, Long.class, Username) / 1024;
        } catch (Exception e) {
            return 0;
        }
    }
}
