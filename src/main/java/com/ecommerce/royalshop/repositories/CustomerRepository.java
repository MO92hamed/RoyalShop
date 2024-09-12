package com.ecommerce.royalshop.repositories;

import com.ecommerce.royalshop.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
