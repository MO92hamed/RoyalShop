package com.ecommerce.royalshop.services.impl;

import com.ecommerce.royalshop.models.Order;
import com.ecommerce.royalshop.repositories.OrderRepository;
import com.ecommerce.royalshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    @Override
    public Order findOrderById(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Order with id [%s] is not found in our database", id)));
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }
}
