package com.javaconnectoracle.filemanager.service.adminservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TablespaceService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getTablespace() {
        String sql = "SELECT * FROM TABLE(get_tablespaces_info)";
        return jdbcTemplate.queryForList(sql);
    }

    public void addTablespace(String tablespace_name) {
        try {
            String sql = "CALL create_tablespace('" + tablespace_name + "')";
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTablespace(String tablespace_name) {
        try {
            String sql = "CALL drop_tablespace('" + tablespace_name + "')";
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Map<String, Object>> getDatafile(String tablespace_name) {
        String sql = "SELECT * FROM TABLE(get_data_files_info('" + tablespace_name + "'))";
        return jdbcTemplate.queryForList(sql);
    }
    public void addDatafile(String tablespace_name, String datafile_name, int size) {
        try {
            String sql = "CALL add_datafile('" + tablespace_name + "', '" + datafile_name + "', " + size + ")";
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void dropDatafile(String tablespace_name, String datafile_name) {
        try {
            String sql = "CALL drop_datafile('" + tablespace_name + "', '" + datafile_name + "')";
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
