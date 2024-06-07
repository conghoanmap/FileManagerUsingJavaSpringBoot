package com.javaconnectoracle.filemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ManagerService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Database
    public List<Map<String, Object>> getDatabase() {
        String sql = "SELECT * FROM TABLE(get_database_info)";
        return jdbcTemplate.queryForList(sql);
    }

    // SGA
    public List<Map<String, Object>> getSGA() {
        String sql = "SELECT * FROM TABLE(get_sga_info)";
        return jdbcTemplate.queryForList(sql);
    }

    // PGA
    public List<Map<String, Object>> getPGA() {
        String sql = "SELECT * FROM TABLE(get_pga_stat_info)";
        return jdbcTemplate.queryForList(sql);
    }

    // Instance
    public List<Map<String, Object>> getInstance() {
        String sql = "SELECT * FROM TABLE(get_instance_info)";
        return jdbcTemplate.queryForList(sql);
    }

    // Spfile
    public List<Map<String, Object>> getSpfile() {
        String sql = "SELECT * FROM TABLE(get_spfile_parameter)";
        return jdbcTemplate.queryForList(sql);
    }

    // Controlfile
    public List<Map<String, Object>> getControlfile() {
        String sql = "SELECT * FROM TABLE(get_control_files_parameter)";
        return jdbcTemplate.queryForList(sql);
    }

    // Tablespace
    public List<Map<String, Object>> getTablespace() {
        String sql = "SELECT * FROM TABLE(get_tablespaces_info)";
        return jdbcTemplate.queryForList(sql);
    }

    // Datafile
    public List<Map<String, Object>> getDatafile(String tablespace_name) {
        String sql = "SELECT * FROM TABLE(get_data_files_info('" + tablespace_name + "'))";
        return jdbcTemplate.queryForList(sql);
    }

    // Users
    public List<Map<String, Object>> getAllUser() {
        String sql = "SELECT * FROM adminfilemng.V_USERS";
        return jdbcTemplate.queryForList(sql);
    }

    // Files
    public List<Map<String, Object>> getAllFile() {
        String sql = "SELECT * FROM adminfilemng.V_FILES";
        return jdbcTemplate.queryForList(sql);
    }

    // Folders
    public List<Map<String, Object>> getAllFolder() {
        String sql = "SELECT * FROM adminfilemng.V_FOLDERS";
        return jdbcTemplate.queryForList(sql);
    }

    // File Types
    public List<Map<String, Object>> getAllFileType() {
        String sql = "SELECT * FROM adminfilemng.V_FILE_TYPES";
        return jdbcTemplate.queryForList(sql);
    }

    // Session
    public List<Map<String, Object>> getAllSession() {
        String sql = "SELECT * FROM V_SESSION";
        return jdbcTemplate.queryForList(sql);
    }

    // Kill session
    public boolean killSession(int sid, int serial) {
        String sql = "CALL kill_session('" + sid + "', '" + serial + "')";
        jdbcTemplate.update(sql);
        return true;
    }

    public List<Map<String, Object>> getLoginHistory() {
        String sql = "SELECT * FROM V_LOGHISTORY";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getSysAction() {
        String sql = "SELECT * FROM V_SYSACTION";
        return jdbcTemplate.queryForList(sql);
    }

    public void truncateLogHistory() {
        String sql = "CALL TRUNCATE_LOGHISTORY()";
        jdbcTemplate.update(sql);
    }
}
