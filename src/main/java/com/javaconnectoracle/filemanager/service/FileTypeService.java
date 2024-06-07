package com.javaconnectoracle.filemanager.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.javaconnectoracle.filemanager.entity.FileTypeEntity;

@Service
public class FileTypeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static FileTypeEntity getFileType(Connection conn, String file_type) {
        String sql = "SELECT * FROM adminfilemng.V_FILE_TYPES WHERE TYPE_ID = '" + file_type + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                FileTypeEntity fileType = new FileTypeEntity();
                fileType.setTYPE_ID(rs.getString("TYPE_ID"));
                fileType.setTYPE_NAME(rs.getString("TYPE_NAME"));
                fileType.setCLASS_ICON(rs.getString("CLASS_ICON"));
                return fileType;
            }
        } catch (Exception e) {
            System.out.println("Get file type err: " + e.getMessage());
        }
        return null;
    }
    public static List<FileTypeEntity> getAllFileType(Connection conn) {
        List<FileTypeEntity> fileTypeEntity = new ArrayList<>();
        String sql = "SELECT * FROM adminfilemng.V_FILE_TYPES";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FileTypeEntity fileType = new FileTypeEntity();
                fileType.setTYPE_ID(rs.getString("TYPE_ID"));
                fileType.setTYPE_NAME(rs.getString("TYPE_NAME"));
                fileType.setCLASS_ICON(rs.getString("CLASS_ICON"));
                fileTypeEntity.add(fileType);
            }
        } catch (Exception e) {
            System.out.println("Get all file type err: " + e.getMessage());
        }
        return fileTypeEntity;
    }
    public String getClassIcon(Connection conn, String file_type) {
        String sql = "SELECT CLASS_ICON FROM adminfilemng.V_FILE_TYPES WHERE TYPE_ID = '" + file_type + "'";
        return jdbcTemplate.queryForObject(sql, String.class);
    }
    public void addFileType(FileTypeEntity fileType) {
        String sql = "CALL insert_into_file_types(?, ?, ?)";
        try {
            jdbcTemplate.update(sql, fileType.getTYPE_ID(), fileType.getTYPE_NAME(), fileType.getCLASS_ICON());
        } catch (Exception e) {
            System.out.println("Add file type err: " + e.getMessage());
        }
    }
    public void updateFileType(FileTypeEntity fileType) {
        String sql = "CALL update_file_types(?, ?, ?)";
        try {
            jdbcTemplate.update(sql, fileType.getTYPE_ID(), fileType.getTYPE_NAME(), fileType.getCLASS_ICON());
        } catch (Exception e) {
            System.out.println("Update file type err: " + e.getMessage());
        }
    }
    public void deleteFileType(String file_type) {
        String sql = "CALL delete_from_file_types(?)";
        try {
            jdbcTemplate.update(sql, file_type);
        } catch (Exception e) {
            System.out.println("Delete file type err: " + e.getMessage());
        }
    }
}
