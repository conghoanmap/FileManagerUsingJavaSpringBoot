package com.javaconnectoracle.filemanager.service.adminservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getAllPolicy() {
        String sql = "SELECT * FROM V_DBA_AUDIT_POLICIES";
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> getPolicy(String policyName) {
        String sql = "SELECT * FROM V_DBA_AUDIT_POLICIES WHERE POLICY_NAME = '" + policyName.toUpperCase() + "'";
        return jdbcTemplate.queryForMap(sql);
    }

    public List<Map<String, Object>> getAuditTrail(String policyName) {
        String sql = "SELECT * FROM V_DBA_FGA_AUDIT_TRAIL WHERE POLICY_NAME = '" + policyName.toUpperCase() + "'";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getAuditUser(String Username) {
        String sql = "SELECT * FROM V_DBA_FGA_AUDIT_TRAIL WHERE DB_USER = '" + Username.toUpperCase() + "'";
        return jdbcTemplate.queryForList(sql);
    }

    public String accessWarning(String Username) {
        String sql = "SELECT check_insert_files('" + Username + "') FROM DUAL";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public void changestatus(String object_schema, String object_name, String policy_name) {
        String sql = "CALL change_policy_status('" + object_schema + "', '" + object_name + "', '" + policy_name + "')";
        jdbcTemplate.update(sql);
    }

    public void clearDataAuditTrail(String policy_name) {
        String sql = "CALL clear_audit_trail('" + policy_name + "')";
        jdbcTemplate.update(sql);
    }
}
