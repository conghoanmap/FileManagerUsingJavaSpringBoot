package com.javaconnectoracle.filemanager.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import com.javaconnectoracle.filemanager.entity.FileEntity;
import com.javaconnectoracle.filemanager.entity.FolderEntity;
import com.javaconnectoracle.filemanager.entity.PasswordChange;
import com.javaconnectoracle.filemanager.entity.UserEntity;
import com.javaconnectoracle.filemanager.service.AuthService;
import com.javaconnectoracle.filemanager.service.ConnectionService;
import com.javaconnectoracle.filemanager.service.FileService;
import com.javaconnectoracle.filemanager.service.FileTypeService;
import com.javaconnectoracle.filemanager.service.FolderService;
import com.javaconnectoracle.filemanager.service.UserService;
import com.javaconnectoracle.filemanager.util.FileDownloadUtil;
import com.javaconnectoracle.filemanager.util.FileUploadUtil;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private FolderService folderService;

    @RequestMapping("")
    public String home(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Connection
            Connection conn = (Connection) session.getAttribute("conn");
            // User
            UserEntity user = (UserEntity) session.getAttribute("current_user");
            model.addAttribute("user", user);
            String last_login = session.getAttribute("last_login").toString();
            model.addAttribute("last_login", last_login);

            // Folder
            int current_folder = FolderService.getFolderId(conn, user.getUSERNAME());
            if (current_folder == 0) {
                model.addAttribute("error_message", "Bạn không thế xem các thư mục của mình!!!");
                return "index";
            }
            Object ss_current_older = session.getAttribute("current_folder");
            if (ss_current_older != null) {
                current_folder = Integer.valueOf(ss_current_older.toString());
            } else {
                session.setAttribute("current_folder", current_folder);// Cập nhật folder hiện tại đang đứng
            }
            model.addAttribute("current_folder", current_folder);
            model.addAttribute("folder_id", current_folder);
            FolderEntity folder = FolderService.getFolder(conn, current_folder);
            model.addAttribute("folder_path", folder.getFOLDER_PATH());
            model.addAttribute("folder_name", folder.getFOLDER_NAME());
            model.addAttribute("folders", folderService.getAllFolder(conn, user.getUSERNAME(), current_folder));
            model.addAttribute("all_folders", folderService.getAllFolder(conn, user.getUSERNAME(), 0));

            // File
            List<FileEntity> files = fileService.getAllFile(conn, current_folder);
            if (files == null) {
                model.addAttribute("error_message", "Không thể xem file trong thư mục này!!!");
                return "index";
            }
            model.addAttribute("files", files);
            model.addAttribute("size_user", folderService.sizeOfFolder(user.getUSERNAME()));

            return "index";
        } catch (Exception e) {
            if (e.getMessage().contains("Closed Connection") || e.getMessage()
                    .contains("An established connection was aborted by the software in your host machine")) {
                redirectAttributes.addFlashAttribute("error_message", "Kết nối đã bị đóng, vui lòng đăng nhập lại!!!");
                Connection connection = (Connection) session.getAttribute("conn");
                ConnectionService.Disconnect(connection);
                session.invalidate();
                return "redirect:/auth/login";
            }
            model.addAttribute("error_message", "Xảy ra ngoại lệ: " + e.getMessage());
            return "index";
        }
    }

    @GetMapping("/movefolder")
    public String moveFolder(HttpSession session, @RequestParam("folderId") int folderId,
            RedirectAttributes redirectAttributes) {
        session.setAttribute("current_folder", folderId);
        redirectAttributes.addFlashAttribute("current_folder", folderId);
        return "redirect:/home";
    }

    @PostMapping("/upload")
    @SuppressWarnings("null")
    public String uploadFile(HttpSession session, @RequestParam("file") MultipartFile multipartFile,
            RedirectAttributes redirectAttributes)
            throws IOException {
        // Xử lý upload
        try {
            Connection conn = (Connection) session.getAttribute("conn");
            int currentFolderId = Integer.valueOf(session.getAttribute("current_folder").toString());
            FolderEntity currentFolder = FolderService.getFolder(conn, currentFolderId);
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            long size = multipartFile.getSize();
            String fileCode = FileUploadUtil.saveFile(fileName, multipartFile,
                    currentFolder.getFOLDER_PATH() + "/" + currentFolder.getFOLDER_NAME());
            // Thêm thông tin file vào cơ sở dữ liệu
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFILE_NAME(fileName);
            fileEntity.setFILE_SIZE((int) size);
            fileEntity.setFILE_ID(fileCode);
            // Lấy ra các ký tự sau dấu chấm của tên file
            fileEntity.setFILE_TYPE(fileName.substring(fileName.lastIndexOf(".") + 1));
            fileEntity.setPARENT_FOLDER(currentFolderId);
            fileEntity.setFILE_PATH(currentFolder.getFOLDER_PATH() + "/" + currentFolder.getFOLDER_NAME());
            // Thêm vào bảng FILES
            fileService.addFile(conn, fileEntity);
            return "redirect:/home";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("fileErrorMessage", e.getMessage());
            return "redirect:/home";
        }
    }

    @GetMapping("/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode, HttpSession session) {

        FileDownloadUtil downloadUtil = new FileDownloadUtil();
        Connection conn = (Connection) session.getAttribute("conn");
        FileEntity file = FileService.getFile(conn, fileCode);
        System.out.println(file.getFILE_PATH());
        Resource resource = null;
        try {
            resource = downloadUtil.getFileAsResource(fileCode, file.getFILE_PATH());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(resource);
    }

    @PostMapping("/createFolder")
    public String createFolder(HttpSession session, @RequestParam("folderName") String folderName,
            @RequestParam("currentFolderId") int currentFolderId, RedirectAttributes redirectAttributes) {
        try {
            Connection conn = (Connection) session.getAttribute("conn");
            FolderEntity currFolder = FolderService.getFolder(conn, currentFolderId);
            FolderEntity folder = new FolderEntity();
            folder.setFOLDER_NAME(folderName);
            folder.setPARENT_FOLDER(currentFolderId);
            folder.setUSERNAME(currFolder.getUSERNAME());
            folder.setFOLDER_PATH(currFolder.getFOLDER_PATH() + "/" + currFolder.getFOLDER_NAME());
            FolderService.addFolder(conn, folder);
            FolderService.createFolder(folder.getFOLDER_PATH(), folderName);
            return "redirect:/home";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("folderErrorMessage", e.getMessage());
            return "redirect:/home";
        }
    }

    @GetMapping("/deleteFolder")
    public String deleteFolder(HttpSession session, @RequestParam("folderId") int folderId,
            RedirectAttributes redirectAttributes) {
        try {
            Connection conn = (Connection) session.getAttribute("conn");
            FolderEntity folder = FolderService.getFolder(conn, folderId);
            FolderService.deleteFolder(folder.getFOLDER_PATH(), folder.getFOLDER_NAME());
            FolderService.deleteTableFolder(conn, folderId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("folderErrorMessage", e.getMessage());
        }
        return "redirect:/home";
    }

    @GetMapping("/deleteFile")
    public String deleteFile(HttpSession session, @RequestParam("fileId") String fileId,
            RedirectAttributes redirectAttributes) {
        try {
            Connection conn = (Connection) session.getAttribute("conn");
            FileEntity file = FileService.getFile(conn, fileId);
            FileService.deleteFile(file.getFILE_PATH() + '/' + file.getFILE_ID() + "-" + file.getFILE_NAME());
            FileService.deleteTableFile(conn, file.getFILE_ID());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("fileErrorMessage", e.getMessage());
        }
        return "redirect:/home";
    }

    @GetMapping("/detailFile")
    public String detailFile(HttpSession session, @RequestParam("fileId") String fileId, Model model) {
        Connection conn = (Connection) session.getAttribute("conn");
        FileEntity file = FileService.getFile(conn, fileId);
        model.addAttribute("file", file);
        model.addAttribute("filename", file.getFILE_NAME().substring(0, file.getFILE_NAME().lastIndexOf('.')));
        model.addAttribute("filetype", FileTypeService.getFileType(conn, file.getFILE_TYPE()));

        UserEntity user = (UserEntity) session.getAttribute("current_user");
        model.addAttribute("user", user);
        String last_login = session.getAttribute("last_login").toString();
        model.addAttribute("last_login", last_login);
        return "detailFile";
    }

    @PostMapping("/renameFile")
    public String renameFile(HttpSession session, @RequestParam("fileId") String fileId,
            @RequestParam("newname") String newFileName) {
        Connection conn = (Connection) session.getAttribute("conn");
        FileEntity file = FileService.getFile(conn, fileId);

        if (!FileService.renameFile(file.getFILE_PATH() + '/' + file.getFILE_ID() + "-" + file.getFILE_NAME(),
                file.getFILE_ID() + "-" + newFileName + '.' + file.getFILE_TYPE())) {
            return null;
        }
        FileService.renameTableFile(conn, fileId, newFileName + '.' + file.getFILE_TYPE());
        return "redirect:/home/detailFile?fileId=" + fileId;
    }

    @GetMapping("/detailFolder")
    public String detailFolder(HttpSession session, @RequestParam("folderId") int folderId, Model model) {
        Connection conn = (Connection) session.getAttribute("conn");
        FolderEntity folder = FolderService.getFolder(conn, folderId);
        model.addAttribute("folder", folder);
        model.addAttribute("foldername", folder.getFOLDER_NAME());
        model.addAttribute("folderpath", folder.getFOLDER_PATH());
        model.addAttribute("size", folderService.sizeOfFolder(folder.getFOLDER_PATH() + "/" + folder.getFOLDER_NAME()));
        model.addAttribute("countfile",
                folderService.countFileandFolderOfFolder(folder.getFOLDER_PATH() + "/" + folder.getFOLDER_NAME()));

        UserEntity user = (UserEntity) session.getAttribute("current_user");
        model.addAttribute("user", user);
        String last_login = session.getAttribute("last_login").toString();
        model.addAttribute("last_login", last_login);
        return "detailFolder";
    }

    @PostMapping("/renameFolder")
    public String renameFolder(HttpSession session, @RequestParam("folderId") int folderId,
            @RequestParam("newname") String newFolderName) {
        Connection conn = (Connection) session.getAttribute("conn");
        FolderEntity folder = FolderService.getFolder(conn, folderId);
        if (!FolderService.renameFolder(folder.getFOLDER_PATH(), folder.getFOLDER_NAME(), newFolderName)) {
            return null;
        }
        FolderService.renameTableFolder(conn, folderId, newFolderName);
        return "redirect:/home/detailFolder?folderId=" + folderId;
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        UserEntity user = (UserEntity) session.getAttribute("current_user");
        model.addAttribute("user", user);
        String last_login = session.getAttribute("last_login").toString();
        model.addAttribute("last_login", last_login);
        return "profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(HttpSession session, @ModelAttribute UserEntity user) {
        Connection conn = (Connection) session.getAttribute("conn");

        UserEntity current_user = (UserEntity) session.getAttribute("current_user");

        user.setUSERNAME(current_user.getUSERNAME());
        userService.updateUser(user, conn);
        session.setAttribute("current_user", user);
        return "redirect:/home/profile";
    }

    @RequestMapping("/changePassword")
    public String changePassword(HttpSession session, Model model) {

        UserEntity user = (UserEntity) session.getAttribute("current_user");
        model.addAttribute("user", user);
        String last_login = session.getAttribute("last_login").toString();
        model.addAttribute("last_login", last_login);
        return "changePassword";
    }

    @PostMapping("/passwordChange")
    public String passwordChange(HttpSession session,
            @Valid @ModelAttribute("PasswordChange") PasswordChange passwordChange, Model model,
            BindingResult bindingResult) {

        UserEntity user = (UserEntity) session.getAttribute("current_user");
        model.addAttribute("user", user);
        String last_login = session.getAttribute("last_login").toString();
        model.addAttribute("last_login", last_login);
        // String username = session.getAttribute("currusername").toString();
        if (bindingResult.hasErrors()) {
            return "changePassword";
        }
        try {
            authService.changePassword(user.getUSERNAME(), passwordChange);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "changePassword";
        }
        // if (!AuthService.verifyPassword(username, oldPassword)) {
        // bindingResult.rejectValue("oldpassword", "error.user", "Old password is
        // incorrect");
        // System.out.println("Old password is incorrect");
        // return "changePassword";
        // }
        // if (!newPassword.equals(newPasswordConfirm)) {
        // return "changePassword";
        // }
        return "redirect:/auth/logout";

    }

    @RequestMapping("/error")
    public String error(Model model, HttpSession session) {
        return "error";
    }
}
