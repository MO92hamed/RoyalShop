package com.ecommerce.royalshop.repositories;

import com.ecommerce.royalshop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
