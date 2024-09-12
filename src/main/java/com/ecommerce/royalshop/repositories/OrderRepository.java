package com.ecommerce.royalshop.repositories;

import com.ecommerce.royalshop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
