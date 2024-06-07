package com.javaconnectoracle.filemanager.service;

import java.sql.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaconnectoracle.filemanager.entity.PasswordChange;
import com.javaconnectoracle.filemanager.entity.UserEntity;
import com.javaconnectoracle.filemanager.service.adminservice.PrivilegeService;
import com.javaconnectoracle.filemanager.service.adminservice.UserManagerService;

import jakarta.servlet.http.HttpSession;

@Service
public class AuthService {

    @Autowired
    private UserManagerService userManagerService;
    @Autowired
    private UserService userService;
    @Autowired
    private FolderService folderService;
    @Autowired
    private PrivilegeService privilegeService;

    @SuppressWarnings("resource")
    public boolean checkLogin(HttpSession session) {
        Connection conn = (Connection) session.getAttribute("conn");
        if (conn != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean login(HttpSession session, String username, String password) {
        try {
            Connection conn = ConnectionService.Connect(username, password);// Kết nối
            UserEntity user = userService.getUser(username, conn);
            boolean isCustomer = userService.isCustomer(user.getUSERNAME());
            if (!isCustomer) {
                return false;
            }
            String lastLogin = userManagerService.getLastLogin(user.getUSERNAME());
            session.setAttribute("conn", conn);//Session của connection
            session.setAttribute("last_login", lastLogin);// Session của lần đăng nhập cuối
            session.setAttribute("current_user", user);// Session của User hiện tại
            return true;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void register(UserEntity user, String password) {
        FolderService.createFolder("", user.getUSERNAME());
        boolean createuserresult = userManagerService.createUser(user.getUSERNAME(), password);
        if (!createuserresult) {
            throw new IllegalArgumentException("Tạo user thất bại!!!");
        }
        userManagerService.AddNewUserToTheUserTable(user);
        folderService.CreateFoldersForNewUsers(user.getUSERNAME());
        privilegeService.grantRoleToUser(user.getUSERNAME(), "DEFAULT_USER_ROLE");
    }

    public static boolean verifyPassword(String Username, String Password) {
        Connection conn = ConnectionService.Connect(Username, Password);
        if (conn != null) {
            ConnectionService.Disconnect(conn);
            return true;
        }
        return false;
    }

    public void changePassword(String username, PasswordChange passwordChange) {

        if (!AuthService.verifyPassword(username, passwordChange.getCurrentPassword())) {
            throw new IllegalArgumentException("Mật khẩu cũ không chính xác!!!");
        }

        if (!passwordChange.getNewPassword().equals(passwordChange.getConfirmNewPassword())) {
            throw new IllegalArgumentException("Mật khẩu mới không khớp nhau!!!");
        }
        userManagerService.changePassword(username, passwordChange.getNewPassword());
    }
}
