package com.javaconnectoracle.filemanager.controller.admincontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaconnectoracle.filemanager.service.adminservice.ProfileService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @RequestMapping("/viewallprofile")
    public String viewAllProfile(Model model) {
        model.addAttribute("profiles", profileService.getAllProfile(false));
        return "viewallprofile";
    }
    @GetMapping("/viewdetailprofile")
    public String viewDetailProfile(Model model, String profile_name) {
        model.addAttribute("profile", profileService.getProfile(profile_name));
        model.addAttribute("profile_name", profile_name);
        return "viewdetailprofile";
    }
    @GetMapping("/deleteprofile")
    public String deleteProfile(String profile_name) {
        profileService.deleteProfile(profile_name);
        return "redirect:/profile/viewallprofile";
    }
    @PostMapping("/createprofile")
    public String createProfile(String profile_name) {
        profileService.createProfile(profile_name);
        return "redirect:/profile/viewallprofile";
    }
    @PostMapping("/editprofile")
    public String editProfile(@RequestParam("profile_name") String profile_name, @RequestParam("resource_name") String resource_name,@RequestParam("limit") int limit) {
        profileService.editProfile(profile_name, resource_name, limit);
        return "redirect:/profile/viewdetailprofile?profile_name=" + profile_name;
    }
    @GetMapping("/grantprofile")
    public String grantProfile(String profile_name, String username) {
        profileService.grantProfile(profile_name, username);
        return "redirect:/profile/viewallprofile";
    }
}
