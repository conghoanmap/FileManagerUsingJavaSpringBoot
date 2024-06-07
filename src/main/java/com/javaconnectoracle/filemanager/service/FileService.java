package com.javaconnectoracle.filemanager.service;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaconnectoracle.filemanager.entity.FileEntity;

@Service
public class FileService {

    @Autowired
    private FileTypeService fileTypeService;

    public static FileEntity getFile(Connection connection, String file_id) {
        String sql = "SELECT * FROM adminfilemng.V_FILES WHERE FILE_ID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, file_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                FileEntity file = new FileEntity();
                file.setFILE_ID(rs.getString("FILE_ID"));
                file.setFILE_NAME(rs.getString("FILE_NAME"));
                file.setFILE_SIZE(rs.getInt("FILE_SIZE"));
                file.setFILE_TYPE(rs.getString("FILE_TYPE"));
                file.setUPLOAD_DATE(rs.getDate("UPLOAD_DATE"));
                file.setPARENT_FOLDER(rs.getInt("PARENT_FOLDER"));
                file.setCLASS_ICON(rs.getString("CLASS_ICON"));
                file.setFILE_PATH(rs.getString("FILE_PATH"));
                return file;
            }
        } catch (Exception e) {
            System.out.println("Failed to get file: " + e.getMessage());
        }
        return null;
    }

    public List<FileEntity> getAllFile(Connection connection, int parentFolder) {
        List<FileEntity> fileEntity = new ArrayList<>();
        String sql = "SELECT * FROM adminfilemng.V_FILES";
        if (parentFolder != 0) {
            sql += " WHERE PARENT_FOLDER = " + parentFolder;
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FileEntity file = new FileEntity();
                file.setFILE_ID(rs.getString("FILE_ID"));
                file.setFILE_NAME(rs.getString("FILE_NAME"));
                file.setFILE_SIZE(rs.getInt("FILE_SIZE"));
                file.setFILE_TYPE(rs.getString("FILE_TYPE"));
                file.setUPLOAD_DATE(rs.getDate("UPLOAD_DATE"));
                file.setPARENT_FOLDER(rs.getInt("PARENT_FOLDER"));
                file.setCLASS_ICON(rs.getString("CLASS_ICON"));
                file.setFILE_PATH(rs.getString("FILE_PATH"));
                fileEntity.add(file);
            }
            return fileEntity;
        } catch (Exception e) {
            return null;
        }
    }

    public static FileEntity convertToFileEntity(MultipartFile multipartFile) {
        try {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFILE_NAME(multipartFile.getOriginalFilename());
            fileEntity.setFILE_SIZE((int) multipartFile.getSize());
            // Lấy ra đuôi file
            @SuppressWarnings("null")
            String extension = multipartFile.getOriginalFilename()
                    .substring(multipartFile.getOriginalFilename().lastIndexOf('.') + 1);
            fileEntity.setFILE_TYPE(extension);
            // Ngày giờ hiện tại
            fileEntity.setUPLOAD_DATE(new java.sql.Date(System.currentTimeMillis()));
            return fileEntity;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean addFile(Connection conn, FileEntity file) {
        String sql = "CALL adminfilemng.INSERT_INTO_FILES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, file.getFILE_ID());
            ps.setString(2, file.getFILE_NAME());
            ps.setInt(3, file.getFILE_SIZE());
            ps.setString(4, file.getFILE_TYPE());
            ps.setInt(5, file.getPARENT_FOLDER());
            String class_icon = fileTypeService.getClassIcon(conn, file.getFILE_TYPE());
            ps.setString(6, class_icon);
            ps.setString(7, file.getFILE_PATH());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Failed to add file: " + e.getMessage());
            return false;
        }
    }

    public static boolean uploadFile(File file, String folderPath) {
        try {
            Path path = Paths.get(folderPath + File.separator + file.getName());
            Files.copy(file.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            System.out.println("Failed to upload file: " + e.getMessage());
            return false;
        }
    }

    public static void deleteTableFile(Connection conn, String file_id) {
        String sql = "CALL adminfilemng.DELETE_FROM_FILES(?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, file_id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new IllegalArgumentException("Xóa file không thành công, lỗi: " + e.getMessage());
        }
    }

    public static void deleteFile(String filePath) {
        File fileToDelete = new File(filePath);

        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                return;
            } else {
                throw new IllegalArgumentException("Xóa không thành công!!!");
            }
        } else {
            throw new IllegalArgumentException("File không tồn tại!!!");
        }
    }

    public static boolean renameTableFile(Connection conn, String file_id, String newFileName) {
        String sql = "CALL adminfilemng.RENAME_FILE(?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, file_id);
            ps.setString(2, newFileName);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Failed to rename file: " + e.getMessage());
            return false;
        }
    }

    public static boolean renameFile(String filePath, String newFileName) {
        File file = new File(filePath);
        String newFilePath = filePath.replace(filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length()),
                newFileName);
        File newFile = new File(newFilePath);
        if (file.renameTo(newFile)) {
            return true;
        } else {
            return false;
        }
    }
}
