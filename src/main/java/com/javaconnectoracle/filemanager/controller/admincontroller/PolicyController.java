package com.javaconnectoracle.filemanager.controller.admincontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaconnectoracle.filemanager.service.adminservice.PolicyService;

@Controller
@RequestMapping("/policy")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @GetMapping("/viewall")
    public String viewAllPolicy(Model model) {
        model.addAttribute("policies", policyService.getAllPolicy());
        return "viewallpolicy";
    }
    @GetMapping("/detailpolicytable")
    public String detailPolicyTable(@RequestParam("policy_name") String policy_name,Model model) {
        model.addAttribute("policy", policyService.getPolicy(policy_name));
        model.addAttribute("audittrail", policyService.getAuditTrail(policy_name));
        return "detailpolicytable";
    }
    @GetMapping("/changestatus")
    public String changeStatus(@RequestParam("object_schema") String object_schema, @RequestParam("object_name") String object_name, @RequestParam("policy_name") String policy_name) {
        policyService.changestatus(object_schema, object_name, policy_name);
        return "redirect:/policy/detailpolicytable?policy_name=" + policy_name;
    }
    @GetMapping("/clearaudittrail")
    public String clearAuditTrail(@RequestParam("policy_name") String policy_name) {
        policyService.clearDataAuditTrail(policy_name);
        return "redirect:/policy/detailpolicytable?policy_name=" + policy_name;
    }
}
