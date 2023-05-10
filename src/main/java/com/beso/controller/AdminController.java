package com.beso.controller;

import com.beso.resource.UserResource;
import com.beso.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;


    @GetMapping(value = "/admin/{adminId}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody UserResource getAdminById(@PathVariable("adminId") Integer adminId){
        return adminService.getAdminById(adminId);
    }

    @PutMapping(value = "/admin/{adminId}")
    public @ResponseBody UserResource updateAdmin(@PathVariable("adminId") Integer adminId, @RequestBody UserResource adminResource){
        return adminService.updateAdmin(adminId,adminResource);
    }

    @PostMapping(value = "/admin")
    public @ResponseBody UserResource createAdmin(@RequestBody UserResource admin){
        return adminService.createAdmin(admin);
    }

}
