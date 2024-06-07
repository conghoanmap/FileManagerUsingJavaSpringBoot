package com.javaconnectoracle.filemanager.controller.admincontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javaconnectoracle.filemanager.service.adminservice.PrivilegeService;

@Controller
@RequestMapping("/privilege")
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;

    @RequestMapping("/viewallrole")
    public String viewAllRole(@RequestParam("role_name") String role_name, Model model) {
        model.addAttribute("roles", privilegeService.getAllRole(role_name));
        return "viewallrole";
    }

    @PostMapping("/createRole")
    public String createRole(@RequestParam("role_name") String role_name, Model model) {
        privilegeService.createRole(role_name);
        return "redirect:/privilege/viewallrole?role_name=" + role_name.toUpperCase();
    }

    @PostMapping("/searchRole")
    public String searchRole(@RequestParam("role_name") String role_name, Model model) {
        return "redirect:/privilege/viewallrole?role_name=" + role_name.toUpperCase();
    }

    @GetMapping("/deleteRole")
    public String deleteRole(@RequestParam("role_name") String role_name, Model model) {
        privilegeService.deleteRole(role_name);
        return "redirect:/privilege/viewallrole?role_name=";
    }

    @GetMapping("/viewallprivilegegrantedtorole")
    public String viewAllPrivilegeGrantedToRole(@RequestParam("role_name") String role_name, Model model) {
        model.addAttribute("role_name", role_name);
        model.addAttribute("rolesysprivs", privilegeService.getSysPrivsGrantToRole(role_name));
        model.addAttribute("roletabprivs", privilegeService.getTabPrivsGrantToRole(role_name));

        model.addAttribute("sysprivs", privilegeService.privsSys());
        model.addAttribute("tabprivs", privilegeService.privsTab());
        model.addAttribute("tables", privilegeService.getAllTableAndView());
        model.addAttribute("funcprocs", privilegeService.getAllFuncAndProc());
        return "viewallprivilegegrantedtorole";
    }

    @PostMapping("/grantSysPrivsToRole")
    public String grantSysPrivsToRole(@RequestParam("role_name") String role_name,
            @RequestParam("priv_name") String priv_name, Model model, RedirectAttributes redirectAttributes) {
        try {
            privilegeService.grantSysPrivsToRole(role_name, priv_name);
            return "redirect:/privilege/viewallprivilegegrantedtorole?role_name=" + role_name;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorSysGrant", e.getMessage());
            return "redirect:/privilege/viewallprivilegegrantedtorole?role_name=" + role_name;
        }
    }

    @PostMapping("/grantTabPrivsToRole")
    public String grantTabPrivsToRole(@RequestParam("role_name") String role_name,
            @RequestParam("priv_name") String priv_name, @RequestParam("table_name") String table_name, Model model, RedirectAttributes redirectAttributes) {
        try {
            privilegeService.grantTabPrivsToRole(role_name, priv_name, table_name);
            return "redirect:/privilege/viewallprivilegegrantedtorole?role_name=" + role_name;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorTabGrant", e.getMessage());
            return "redirect:/privilege/viewallprivilegegrantedtorole?role_name=" + role_name;
        }
    }
    @GetMapping("/revokeSysPrivsFromRole")
    public String revokeSysPrivsFromRole(@RequestParam("role_name") String role_name,
            @RequestParam("priv_name") String priv_name, Model model, RedirectAttributes redirectAttributes) {
        try {
            privilegeService.revokeSysPrivsFromRole(role_name, priv_name);
            return "redirect:/privilege/viewallprivilegegrantedtorole?role_name=" + role_name;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorSysRevoke", e.getMessage());
            return "redirect:/privilege/viewallprivilegegrantedtorole?role_name=" + role_name;
        }
    }
    @GetMapping("/revokeTabPrivsFromRole")
    public String revokeTabPrivsFromRole(@RequestParam("role_name") String role_name,
            @RequestParam("priv_name") String priv_name, @RequestParam("table_name") String table_name, Model model, RedirectAttributes redirectAttributes) {
        try {
            privilegeService.revokeTabPrivsFromRole(role_name, priv_name, table_name);
            return "redirect:/privilege/viewallprivilegegrantedtorole?role_name=" + role_name;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorTabRevoke", e.getMessage());
            return "redirect:/privilege/viewallprivilegegrantedtorole?role_name=" + role_name;
        }
    }
    @GetMapping("/viewallprivilegegrantedtouser")
    public String viewAllPrivilegeGrantedToUser(@RequestParam("user_name") String user_name, Model model) {
        model.addAttribute("user_name", user_name);
        model.addAttribute("usersysprivs", privilegeService.getSysPrivsGrantToUser(user_name));
        model.addAttribute("usertabprivs", privilegeService.getTabPrivsGrantToUser(user_name));

        model.addAttribute("sysprivs", privilegeService.privsSys());
        model.addAttribute("tabprivs", privilegeService.privsTab());
        model.addAttribute("tables", privilegeService.getAllTableAndView());
        model.addAttribute("funcprocs", privilegeService.getAllFuncAndProc());
        model.addAttribute("rolesgrant", privilegeService.getRoleGrantToUser(user_name));
        model.addAttribute("roles", privilegeService.getAllRole(""));
        return "viewallprivilegegrantedtouser";
    }
    @PostMapping("/grantSysPrivsToUser")
    public String grantSysPrivsToUser(@RequestParam("user_name") String user_name,
            @RequestParam("priv_name") String priv_name, Model model, RedirectAttributes redirectAttributes) {
        try {
            privilegeService.grantSysPrivsToUser(user_name, priv_name);
            return "redirect:/privilege/viewallprivilegegrantedtouser?user_name=" + user_name;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorSysGrant", e.getMessage());
            return "redirect:/privilege/viewallprivilegegrantedtouser?user_name=" + user_name;
        }
    }
    @PostMapping("/grantTabPrivsToUser")
    public String grantTabPrivsToUser(@RequestParam("user_name") String user_name,
            @RequestParam("priv_name") String priv_name, @RequestParam("table_name") String table_name, Model model, RedirectAttributes redirectAttributes) {
        try {
            privilegeService.grantTabPrivsToUser(user_name, priv_name, table_name);
            return "redirect:/privilege/viewallprivilegegrantedtouser?user_name=" + user_name;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorTabGrant", e.getMessage());
            return "redirect:/privilege/viewallprivilegegrantedtouser?user_name=" + user_name;
        }
    }
    @GetMapping("/revokeSysPrivsFromUser")
    public String revokeSysPrivsFromUser(@RequestParam("user_name") String user_name,
            @RequestParam("priv_name") String priv_name, Model model, RedirectAttributes redirectAttributes) {
        try {
            privilegeService.revokeSysPrivsFromUser(user_name, priv_name);
            return "redirect:/privilege/viewallprivilegegrantedtouser?user_name=" + user_name;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorSysRevoke", e.getMessage());
            return "redirect:/privilege/viewallprivilegegrantedtouser?user_name=" + user_name;
        }
    }
    @GetMapping("/revokeTabPrivsFromUser")
    public String revokeTabPrivsFromUser(@RequestParam("user_name") String user_name,
            @RequestParam("priv_name") String priv_name, @RequestParam("table_name") String table_name, Model model, RedirectAttributes redirectAttributes) {
        try {
            privilegeService.revokeTabPrivsFromUser(user_name, priv_name, table_name);
            return "redirect:/privilege/viewallprivilegegrantedtouser?user_name=" + user_name;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorTabRevoke", e.getMessage());
            return "redirect:/privilege/viewallprivilegegrantedtouser?user_name=" + user_name;
        }
    }
    @PostMapping("/grantRoleToUser")
    public String grantRoleToUser(@RequestParam("user_name") String user_name,
            @RequestParam("role_name") String role_name, Model model, RedirectAttributes redirectAttributes) {
        try {
            privilegeService.grantRoleToUser(user_name, role_name);
            return "redirect:/privilege/viewallprivilegegrantedtouser?user_name=" + user_name;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorRoleGrant", e.getMessage());
            return "redirect:/privilege/viewallprivilegegrantedtouser?user_name=" + user_name;
        }
    }
    @GetMapping("/revokeRoleFromUser")
    public String revokeRoleFromUser(@RequestParam("user_name") String user_name,
            @RequestParam("role_name") String role_name, Model model, RedirectAttributes redirectAttributes) {
        try {
            privilegeService.revokeRoleFromUser(user_name, role_name);
            return "redirect:/privilege/viewallprivilegegrantedtouser?user_name=" + user_name;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorRoleRevoke", e.getMessage());
            return "redirect:/privilege/viewallprivilegegrantedtouser?user_name=" + user_name;
        }
    }
}
