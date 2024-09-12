package com.ecommerce.royalshop.controllers;

import com.ecommerce.royalshop.models.Admin;
import com.ecommerce.royalshop.services.impl.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminServiceImpl adminService;

    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable Long id){
        return adminService.findAdminById(id);
    }

    @GetMapping
    public List<Admin> getAllAdmins(){
        return adminService.findAllAdmins();
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteAdmin(@PathVariable Long id){
        Map<String, Boolean> response = new HashMap<>();
        adminService.deleteAdminById(id);
        try {
            response.put("Deleted", Boolean.TRUE);
            return response;
        }catch (Exception e){
            response.put("Failed to delete", Boolean.FALSE);
            return response;
        }
    }

    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin){
        return adminService.createAdmin(admin);
    }

    @PutMapping("/{id}")
    public Admin updateAdmin(@PathVariable Long id ,@RequestBody Admin admin){
        Admin admin1 = adminService.findAdminById(id);
        if (admin1 != null){
            return adminService.updateAdmin(admin);
        }else 
            throw new RuntimeException("Failed to update this admin");

    }

}
