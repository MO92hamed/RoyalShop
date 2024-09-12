package com.ecommerce.royalshop.repositories;

import com.ecommerce.royalshop.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
