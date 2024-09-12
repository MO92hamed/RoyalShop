package com.ecommerce.royalshop.services;

import com.ecommerce.royalshop.models.Order;

import java.util.List;

public interface OrderService {
    Order findOrderById(Long id);
    List<Order> findAllOrders();
    void deleteOrderById(Long id);
    Order createOrder(Order order);
    Order updateOrder(Order order);
}
