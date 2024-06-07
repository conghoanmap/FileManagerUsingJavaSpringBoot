package com.javaconnectoracle.filemanager.controller.admincontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaconnectoracle.filemanager.service.adminservice.PolicyService;
import com.javaconnectoracle.filemanager.service.adminservice.ProfileService;
import com.javaconnectoracle.filemanager.service.adminservice.UserManagerService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PolicyService policyService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserManagerService userManagerService;

    @RequestMapping("/viewall")
    public String viewAllUser(Model model) {
        model.addAttribute("users", userManagerService.getAllUser());
        return "viewalluser";
    }

    @GetMapping("/dropUser")
    public String dropUser(@RequestParam("username") String username) {
        userManagerService.dropUser(username);
        return "redirect:/user/viewall";
    }

    @GetMapping("/viewdetail")
    public String viewDetailUser(Model model, String username) {
        model.addAttribute("user", userManagerService.getUser(username));
        model.addAttribute("username", username);
        model.addAttribute("profiles", profileService.getAllProfile(true));
        model.addAttribute("audittrail", policyService.getAuditUser(username));
        return "viewdetailuser";
    }
    @PostMapping("/grantProfile")
    public String grantProfile(@RequestParam("username") String username, @RequestParam("profile_name") String profile) {
        profileService.grantProfile(profile, username);
        return "redirect:/user/viewdetail?username=" + username;
    }

}
