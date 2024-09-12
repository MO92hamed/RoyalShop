package com.ecommerce.royalshop.services.impl;

import com.ecommerce.royalshop.models.Admin;
import com.ecommerce.royalshop.repositories.AdminRepository;
import com.ecommerce.royalshop.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
 @RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    @Override
    public Admin findAdminById(Long id) {
        return adminRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Admin with id [%s] is not found in our database", id)));
    }

    @Override
    public List<Admin> findAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public void deleteAdminById(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin updateAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
}
