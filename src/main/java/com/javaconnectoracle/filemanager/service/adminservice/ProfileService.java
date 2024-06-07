package com.javaconnectoracle.filemanager.service.adminservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getAllProfile(boolean isDefault) {
        String sql = "";
        if (isDefault) {
            sql = "SELECT * FROM TABLE(get_unique_profiles())";
        } else {
            sql = "SELECT * FROM TABLE(get_unique_profiles()) WHERE PROFILE_NAME != 'DEFAULT'";
        }
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getProfile(String profile_name) {
        String sql = "SELECT * FROM TABLE(get_profile_resources('" + profile_name
                + "'))  WHERE RESOURCE_NAME !='PASSWORD_VERIFY_FUNCTION'";
        return jdbcTemplate.queryForList(sql);
    }

    public void createProfile(String profile_name) {
        jdbcTemplate.execute("ALTER SESSION SET \"_ORACLE_SCRIPT\"=true");
        String sql = "CALL create_profile('" + profile_name + "')";
        jdbcTemplate.update(sql);
    }

    public void editProfile(String profile_name, String resource_name, int limit) {
        String sql = "CALL edit_profile_limit('" + profile_name + "', '" + resource_name + "', " + limit + ")";
        jdbcTemplate.update(sql);
    }

    public void deleteProfile(String profile_name) {
        String sql = "CALL delete_profile('" + profile_name + "')";
        jdbcTemplate.update(sql);
    }

    public void grantProfile(String profile_name, String username) {
        String sql = "CALL grant_profile_to_user('" + username + "', '" + profile_name + "')";
        jdbcTemplate.update(sql);
    }

}
