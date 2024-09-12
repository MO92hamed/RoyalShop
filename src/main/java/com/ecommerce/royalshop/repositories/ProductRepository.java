package com.ecommerce.royalshop.repositories;

import com.ecommerce.royalshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
