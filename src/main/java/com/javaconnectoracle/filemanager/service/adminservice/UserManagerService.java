package com.javaconnectoracle.filemanager.service.adminservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.javaconnectoracle.filemanager.entity.UserEntity;

@Service
public class UserManagerService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getAllUser() {
        String sql = "SELECT * FROM V_DBA_USERS";
        return jdbcTemplate.queryForList(sql);
    }

    // Xem 1 user, trả về 1 object
    public Map<String, Object> getUser(String username) {
        String sql = "SELECT * FROM TABLE(get_user_info('" + username + "'))";
        return jdbcTemplate.queryForMap(sql);
    }

    public boolean createUser(String username, String password) {
        jdbcTemplate.execute("ALTER SESSION SET \"_ORACLE_SCRIPT\"=TRUE");
        String sql = "CALL create_user('" + username + "', '" + password + "')";
        jdbcTemplate.update(sql);
        return true;
    }

    public boolean dropUser(String username) {
        jdbcTemplate.execute("ALTER SESSION SET \"_ORACLE_SCRIPT\"=TRUE");
        String sql = "CALL drop_user('" + username + "')";
        jdbcTemplate.update(sql);
        return true;
    }

    public void changePassword(String username, String newPassword) {
        String sql = "CALL change_password('" + username + "', '" + newPassword + "')";
        jdbcTemplate.execute(sql);
    }

    public List<Map<String, Object>> getAllUserLocked() {
        String sql = "SELECT * FROM V_LOCKED_USER";
        return jdbcTemplate.queryForList(sql);
    }

    public boolean unlockUser(String username) {
        String sql = "CALL unlock_user('" + username + "')";
        jdbcTemplate.execute(sql);
        return true;
    }

    @SuppressWarnings("deprecation")
    public String getLastLogin(String username) {
        try {
            String sql = "SELECT get_last_login(?) FROM DUAL";
            return jdbcTemplate.queryForObject(sql, new Object[] { username }, String.class);
        } catch (Exception e) {
            return "Failed...";
        }
    }

    public void AddNewUserToTheUserTable(UserEntity user) {
        String sql = "CALL insert_user('" + user.getUSERNAME() + "', '" + user.getFULLNAME() + "', '" + user.getPHONE()
                + "', '" + user.getEMAIL() + "')";
        jdbcTemplate.update(sql);
    }
}
