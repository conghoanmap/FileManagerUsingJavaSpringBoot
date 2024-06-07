package com.javaconnectoracle.filemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.javaconnectoracle.filemanager.service.ManagerService;
import com.javaconnectoracle.filemanager.service.adminservice.TablespaceService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService mngService;
    @Autowired
    private TablespaceService tablespaceService;

    @RequestMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        model.addAttribute("database", mngService.getDatabase());
        model.addAttribute("sga", mngService.getSGA());
        model.addAttribute("pga", mngService.getPGA());
        model.addAttribute("instance", mngService.getInstance());
        model.addAttribute("spfile", mngService.getSpfile());
        model.addAttribute("controlfile", mngService.getControlfile());
        model.addAttribute("tablespace", mngService.getTablespace());
        return "dashboard";
    }

    @RequestMapping("/detailtablespace")
    public String detailtablespace(@RequestParam("tablespace_name") String tablespace_name, Model model) {
        model.addAttribute("tablespace_name", tablespace_name);
        model.addAttribute("datafile", mngService.getDatafile(tablespace_name));
        return "detailtablespace";
    }

    @PostMapping("/addtablespace")
    public String addtablespace(@RequestParam("tablespace_name") String tablespace_name) {
        tablespaceService.addTablespace(tablespace_name);
        return "redirect:/manager/dashboard";
    }

    @GetMapping("/deletetablespace")
    public String deletetablespace(@RequestParam("tablespace_name") String tablespace_name) {
        tablespaceService.deleteTablespace(tablespace_name);
        return "redirect:/manager/dashboard";
    }

    @PostMapping("/adddatafile")
    public String adddatafile(@RequestParam("tablespace_name") String tablespace_name,
            @RequestParam("datafile_name") String datafile_name, @RequestParam("size") int size) {
        tablespaceService.addDatafile(tablespace_name, datafile_name, size);
        return "redirect:/manager/detailtablespace?tablespace_name=" + tablespace_name;
    }

    @GetMapping("/dropdatafile")
    public String dropdatafile(@RequestParam("tablespace_name") String tablespace_name,
            @RequestParam("datafile_name") String datafile_name) {
        datafile_name = datafile_name.replace("//", "\\");
        tablespaceService.dropDatafile(tablespace_name, datafile_name);
        return "redirect:/manager/detailtablespace?tablespace_name=" + tablespace_name;
    }

    @RequestMapping("/viewalltable")
    public String viewAllTable(Model model) {
        model.addAttribute("users", mngService.getAllUser());
        model.addAttribute("files", mngService.getAllFile());
        model.addAttribute("folders", mngService.getAllFolder());
        model.addAttribute("filetypes", mngService.getAllFileType());
        return "viewalltable";
    }
    @RequestMapping("/viewallsession")
    public String viewAllSession(Model model) {
        model.addAttribute("sessions", mngService.getAllSession());
        return "viewallsession";
    }
    @GetMapping("/killSession")
    public String killSession(@RequestParam("sid") int sid, @RequestParam("serial") int serial) {
        mngService.killSession(sid, serial);
        return "redirect:/manager/viewallsession";
    }
    @RequestMapping("/actionsystem")
    public String actionSystem(Model model) {
        model.addAttribute("loghistories", mngService.getLoginHistory());
        model.addAttribute("sysactions", mngService.getSysAction());
        return "actionsystem";
    }
    @GetMapping("/truncate")
    public String truncate() {
        mngService.truncateLogHistory();
        return "redirect:/manager/actionsystem";
    }
}
