package com.javaconnectoracle.filemanager.controller;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javaconnectoracle.filemanager.entity.UserEntity;
import com.javaconnectoracle.filemanager.service.AuthService;
import com.javaconnectoracle.filemanager.service.ConnectionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/loginweb")
    public String loginweb(HttpServletRequest request, @RequestParam("username") String username,
            @RequestParam("password") String password, RedirectAttributes redirectAttributes) {

        try {
            HttpSession session = request.getSession();
            boolean isCustomer = authService.login(session, username, password);
            if(isCustomer) {
                // Nếu là khách hàng thì chuyển hướng về trang home
                return "redirect:/home";
            }
            else{
                // Nếu là quản lý thì chuyển hướng về trang dashboard
                return "redirect:/manager/dashboard";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/auth/login";// Nếu có lỗi thì chuyển hướng về trang login
        }
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/registerweb")
    public String registerweb(@ModelAttribute UserEntity user, @RequestParam("PASSWORD") String password,
            @RequestParam("CONFIRMPASSWORD") String confirmpassword, Model model) {
        if (user.getUSERNAME().isEmpty()) {
            model.addAttribute("errorMessage", "Vui lòng nhập tên đăng nhập");
            return "register";
        }
        if (password.isEmpty()) {
            model.addAttribute("errorMessage", "Vui lòng nhập mật khẩu");
            return "register";
        }
        if (confirmpassword.isEmpty()) {
            model.addAttribute("errorMessage", "Vui lòng xác nhận mật khẩu");
            return "register";
        } else {
            if (password.compareTo(confirmpassword) != 0) {
                model.addAttribute("errorMessage", "Mật khẩu không khớp");
                return "register";
            }
        }
        try {
            authService.register(user, password);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
        return "redirect:/auth/login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // Viết lệnh để đăng xuất
        try {
            Connection connection = (Connection) session.getAttribute("conn");
            ConnectionService.Disconnect(connection);
            session.invalidate();
            return "redirect:/auth/login";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/home";
        }
    }
}
