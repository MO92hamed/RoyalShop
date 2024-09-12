package com.ecommerce.royalshop.services;

import com.ecommerce.royalshop.models.Admin;

import java.util.List;

public interface AdminService {
    Admin findAdminById(Long id);
    List<Admin> findAllAdmins();
    void deleteAdminById(Long id);
    Admin createAdmin(Admin admin);
    Admin updateAdmin(Admin admin);
}
