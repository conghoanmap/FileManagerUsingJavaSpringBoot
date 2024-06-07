package com.javaconnectoracle.filemanager.service;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.javaconnectoracle.filemanager.entity.FolderEntity;

@Service
public class FolderService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean CreateFoldersForNewUsers(String Username) {
        try {
            String sql = "CALL insert_into_folders('" + Username + "')";
            jdbcTemplate.execute(sql);
            return true;
        } catch (JDBCException e) {
            System.out.println("Create folders failed: " + e.getMessage());
            return false;
        }
    }

    public static void createFolder(String currFolder, String name) {
        String folderPath = "";
        if (currFolder == "") {
            folderPath = "C:\\Users\\ADMIN\\Máy tính\\LearnSpring\\filemanagercp\\data";
        } else {
            folderPath = "C:\\Users\\ADMIN\\Máy tính\\LearnSpring\\filemanagercp\\" + currFolder;
        }

        Path folderPathh = Paths.get(folderPath, name);
        int counter = 1;

        // Kiểm tra xem thư mục đã tồn tại chưa, nếu có thì thêm số thứ tự
        while (Files.exists(folderPathh)) {
            folderPathh = Paths.get(folderPath, name + "-(" + counter + ")");
            counter++;
        }
        try {
            Files.createDirectories(folderPathh);
        } catch (IOException e) {
            System.out.println("Tao folder that bai: " + e.getMessage());
            throw new IllegalArgumentException("Tao folder that bai: " + e.getMessage());
        }
    }

    public static FolderEntity getFolder(Connection connection, int folderId) {
        String sql = "SELECT * FROM adminfilemng.V_FOLDERS WHERE FOLDER_ID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, folderId);
            ResultSet rs = ps.executeQuery();
            FolderEntity folder = new FolderEntity();
            if (rs.next()) {
                folder.setFOLDER_ID(rs.getInt("FOLDER_ID"));
                folder.setFOLDER_NAME(rs.getString("FORLDER_NAME"));
                folder.setPARENT_FOLDER(rs.getInt("PARENT_FOLDER"));
                folder.setUSERNAME(rs.getString("USERNAME"));
                folder.setFOLDER_PATH(rs.getString("FOLDER_PATH"));
            }
            return folder;
        } catch (SQLException e) {
            return null;
            // throw new IllegalArgumentException("Get Folder Error: " + e.getMessage());
        }
    }

    public static int getFolderId(Connection connection, String FOLDER_NAME) {
        String sql = "SELECT FOLDER_ID FROM adminfilemng.V_FOLDERS WHERE FORLDER_NAME = '" + FOLDER_NAME.toLowerCase()
                + "' AND PARENT_FOLDER IS NULL";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int folder_id = 0;
            if (rs.next()) {
                folder_id = rs.getInt("FOLDER_ID");
            }
            return folder_id;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<FolderEntity> getAllFolder(Connection connection, String username, int parrentFolder) {
        List<FolderEntity> folderEntity = new ArrayList<>();
        String sql = "SELECT * FROM adminfilemng.V_FOLDERS WHERE USERNAME = '" + username.toLowerCase() + "'";
        if (parrentFolder > 0) {
            sql += " AND PARENT_FOLDER = " + parrentFolder + "";
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FolderEntity folder = new FolderEntity();
                folder.setFOLDER_ID(rs.getInt("FOLDER_ID"));
                folder.setFOLDER_NAME(rs.getString("FORLDER_NAME"));
                folder.setPARENT_FOLDER(rs.getInt("PARENT_FOLDER"));
                folder.setUSERNAME(rs.getString("USERNAME"));
                folder.setFOLDER_PATH(rs.getString("FOLDER_PATH"));
                folderEntity.add(folder);
            }
            return folderEntity;
        } catch (SQLException e) {
            return null;
        }
    }

    // table
    public static void addFolder(Connection connection, FolderEntity folder) {
        String sql = "CALL adminfilemng.FOLDER_PKG.INSERT_INTO_FOLDERS(?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, folder.getFOLDER_NAME());
            ps.setInt(2, folder.getPARENT_FOLDER());
            ps.setString(3, folder.getUSERNAME());
            ps.setString(4, folder.getFOLDER_PATH());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add Folder Error: " + e.getMessage());
            throw new IllegalArgumentException("Add Folder Error: " + e.getMessage());
        }
    }

    public static void deleteTableFolder(Connection conn, int folderId) {
        String sql = "CALL adminfilemng.FOLDER_PKG.DELETE_FROM_FOLDERS(?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, folderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete Folder Error: " + e.getMessage());
        }
    }

    public static void deleteFolder(String folderPath, String folderName) {
        Path path = Paths.get(folderPath, folderName);
        if (Files.exists(path)) {
            try {
                Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                throw new IllegalArgumentException("Xóa folder không thành công, lỗi: " + e.getMessage());
            }
        }
        else{
            throw new IllegalArgumentException("Folder không tồn tại!!!");
        }
    }

    public int sizeOfFolder(String folderPath) {// Về dạng FUNCTION
        String sql = "SELECT adminfilemng.get_total_file_size('" + folderPath + "') FROM DUAL";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result != null ? result : 0;
    }

    public int countFileandFolderOfFolder(String folderPath) {// Về dạng FUNCTION
        String sql = "SELECT adminfilemng.count_files_and_folders('" + folderPath + "') FROM DUAL";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result != null ? result : 0;
    }

    public static boolean renameFolder(String folderPath, String oldName, String newName) {
        Path oldPath = Paths.get(folderPath, oldName);
        Path newPath = Paths.get(folderPath, newName);
        if (Files.exists(oldPath)) {
            try {
                Files.move(oldPath, newPath);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean renameTableFolder(Connection conn, int folderId, String newName) {
        String sql = "CALL adminfilemng.FOLDER_PKG.RENAME_FOLDER(?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, folderId);
            ps.setString(2, newName);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Rename Folder Error: " + e.getMessage());
        }
        return false;
    }
}
