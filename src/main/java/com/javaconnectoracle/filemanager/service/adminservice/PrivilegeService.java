package com.javaconnectoracle.filemanager.service.adminservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getAllRole(String role_name) {
        String sql = "SELECT * FROM TABLE(get_roles)";
        if (role_name.isEmpty() == false) {
            sql += " WHERE ROLE LIKE '%" + role_name + "%'";
        }
        return jdbcTemplate.queryForList(sql);
    }

    public void createRole(String role_name) {
        jdbcTemplate.execute("ALTER SESSION SET \"_ORACLE_SCRIPT\"=true");
        String sql = "CALL create_role('" + role_name.toUpperCase() + "')";
        jdbcTemplate.update(sql);
    }

    public void deleteRole(String role_name) {
        jdbcTemplate.execute("ALTER SESSION SET \"_ORACLE_SCRIPT\"=true");
        String sql = "CALL delete_role('" + role_name.toUpperCase() + "')";
        jdbcTemplate.update(sql);
    }

    public List<Map<String, Object>> getSysPrivsGrantToRole(String role_name) {
        String sql = "SELECT * FROM TABLE(get_role_sys_privs('" + role_name + "'))";
        return jdbcTemplate.queryForList(sql);
    }

    public void revokeSysPrivsFromRole(String role_name, String priv_name) {
        try {
            jdbcTemplate.execute("ALTER SESSION SET \"_ORACLE_SCRIPT\"=true");
            String sql = "CALL revoke_sys_priv_from_role('" + role_name + "', '" + priv_name + "')";
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            throw new IllegalArgumentException("Thu quyền thất bại, kiểm tra quyền được thu(" + priv_name + ")!");
        }
    }

    public List<Map<String, Object>> getTabPrivsGrantToRole(String role_name) {
        String sql = "SELECT * FROM TABLE(get_role_tab_privs('" + role_name + "'))";
        return jdbcTemplate.queryForList(sql);
    }

    public void revokeTabPrivsFromRole(String role_name, String priv_name, String table_name) {
        try {
            jdbcTemplate.execute("ALTER SESSION SET \"_ORACLE_SCRIPT\"=true");
            String sql = "CALL revoke_tab_priv_from_role('" + role_name + "', '" + priv_name
                    + "', '" + table_name + "')";
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Thu quyền thất bại, kiểm tra quyền được thu(" + priv_name + " ON " + table_name + ")!");
        }
    }

    public void grantSysPrivsToRole(String role_name, String priv_name) {
        try {
            jdbcTemplate.execute("ALTER SESSION SET \"_ORACLE_SCRIPT\"=true");
            String sql = "CALL grant_sys_priv_to_role('" + role_name + "', '" + priv_name
                    + "')";
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cấp quyền thất bại, kiểm tra quyền được cấp(" + priv_name + ")!");
        }
    }

    public void grantTabPrivsToRole(String role_name, String priv_name, String table_name) {
        try {
            jdbcTemplate.execute("ALTER SESSION SET \"_ORACLE_SCRIPT\"=true");
            String sql = "CALL grant_tab_priv_to_role('" + role_name + "', '" + priv_name
                    + "', '" + table_name + "')";
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Cấp quyền thất bại, kiểm tra quyền được cấp(" + priv_name + " ON " + table_name + ")!");
        }
    }

    public List<Map<String, Object>> getTabPrivsGrantToUser(String user_name) {
        String sql = "SELECT * FROM V_DBA_TAB_PRIVS WHERE GRANTEE = '" + user_name + "'";
        return jdbcTemplate.queryForList(sql);
    }

    public void revokeTabPrivsFromUser(String user_name, String priv_name, String table_name) {
        try {
            jdbcTemplate.execute("ALTER SESSION SET \"_ORACLE_SCRIPT\"=true");
            String sql = "CALL revoke_tab_priv_from_user('" + user_name + "', '" +
                    priv_name + "', '" + table_name + "')";
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Thu quyền thất bại, kiểm tra quyền được thu(" + priv_name + " ON " + table_name + ")!");
        }
    }

    public void grantTabPrivsToUser(String user_name, String priv_name, String table_name) {
        try {
            jdbcTemplate.execute("ALTER SESSION SET \"_ORACLE_SCRIPT\"=true");
            String sql = "CALL grant_tab_priv_to_user('" + user_name + "', '" + priv_name
                    + "', '" + table_name + "')";
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Cấp quyền thất bại, kiểm tra quyền được cấp(" + priv_name + " ON " + table_name + ")!!");
        }
    }

    public List<Map<String, Object>> getSysPrivsGrantToUser(String user_name) {
        String sql = "SELECT * FROM V_DBA_SYS_PRIVS WHERE GRANTEE = '" + user_name + "'";
        return jdbcTemplate.queryForList(sql);
    }

    public void grantSysPrivsToUser(String user_name, String priv_name) {
        try {
            jdbcTemplate.execute("ALTER SESSION SET \"_ORACLE_SCRIPT\"=true");
            String sql = "CALL grant_sys_priv_to_user('" + user_name + "', '" + priv_name + "')";
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cấp quyền thất bại, kiểm tra quyền được cấp(" + priv_name + ")!");
        }
    }

    public void revokeSysPrivsFromUser(String user_name, String priv_name) {
        try {
            jdbcTemplate.execute("ALTER SESSION SET \"_ORACLE_SCRIPT\"=true");
            String sql = "CALL revoke_sys_priv_from_user('" + user_name + "', '" + priv_name + "')";
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            throw new IllegalArgumentException("Thu quyền thất bại, kiểm tra quyền được thu(" + priv_name + ")!");
        }
    }

    public List<Map<String, Object>> getRoleGrantToUser(String user_name) {
        String sql = "SELECT * FROM V_DBA_ROLE_PRIVS WHERE GRANTEE = '" + user_name + "'";
        return jdbcTemplate.queryForList(sql);
    }

    public void grantRoleToUser(String user_name, String role_name) {
        try {
            jdbcTemplate.execute("ALTER SESSION SET \"_ORACLE_SCRIPT\"=true");
            String sql = "CALL grant_role_to_user('" + user_name + "', '" + role_name + "')";
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Cấp nhóm quyền thất bại, kiểm tra nhóm quyền được cấp(" + role_name + ")!");
        }
    }

    public void revokeRoleFromUser(String user_name, String role_name) {
        try {
            jdbcTemplate.execute("ALTER SESSION SET \"_ORACLE_SCRIPT\"=true");
            String sql = "CALL revoke_role_from_user('" + user_name + "', '" + role_name + "')";
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Thu hồi nhóm quyền thất bại, kiểm tra nhóm quyền được thu(" + role_name + ")!");
        }
    }

    public List<String> privsSys() {
        List<String> privsSystem = new ArrayList<>();
        privsSystem.add("CREATE SESSION");
        privsSystem.add("CREATE TABLE");
        privsSystem.add("CREATE VIEW");
        privsSystem.add("CREATE PROCEDURE");
        privsSystem.add("CREATE SEQUENCE");
        privsSystem.add("CREATE TRIGGER");
        privsSystem.add("CREATE INDEX");
        return privsSystem;
    }

    public List<String> privsTab() {
        List<String> privsTable = new ArrayList<>();
        privsTable.add("SELECT");
        privsTable.add("INSERT");
        privsTable.add("UPDATE");
        privsTable.add("DELETE");
        privsTable.add("EXECUTE");
        return privsTable;
    }

    public List<String> getAllTableAndView() {
        List<String> sysView = new ArrayList<>();
        List<String> tables = new ArrayList<>();
        sysView.add("V_ALL_VIEW");
        sysView.add("V_ALL_TABLES");
        sysView.add("V_PACKAGE");
        for (String string : sysView) {
            String sql = "SELECT * FROM " + string;
            List<Map<String, Object>> funcorprocs = jdbcTemplate.queryForList(sql);
            for (Map<String, Object> map : funcorprocs) {
                tables.add(map.get("TABLE_NAME") != null ? map.get("TABLE_NAME").toString()
                        : map.get("VIEW_NAME") != null ? map.get("VIEW_NAME").toString()
                                : map.get("OBJECT_NAME").toString());
            }
        }
        return tables;
    }

    public List<String> getAllFuncAndProc() {
        String sql = "SELECT * FROM V_FUNC_AND_PROC";
        List<String> funcAndProc = new ArrayList<>();
        List<Map<String, Object>> funcorprocs = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : funcorprocs) {
            funcAndProc.add(map.get("OBJECT_NAME").toString());
        }
        return funcAndProc;
    }
}
