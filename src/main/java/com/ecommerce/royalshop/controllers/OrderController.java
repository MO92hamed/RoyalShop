package com.ecommerce.royalshop.controllers;

import com.ecommerce.royalshop.models.Customer;
import com.ecommerce.royalshop.models.Order;
import com.ecommerce.royalshop.models.Product;
import com.ecommerce.royalshop.services.impl.CustomerServiceImpl;
import com.ecommerce.royalshop.services.impl.OrderServiceImpl;
import com.ecommerce.royalshop.services.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderService;
    private final ProductServiceImpl productService;
    private final CustomerServiceImpl customerService;

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id){
        return orderService.findOrderById(id);
    }

    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.findAllOrders();
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteOrder(@PathVariable Long id){
        Map<String, Boolean> response = new HashMap<>();
        orderService.deleteOrderById(id);
        try {
            response.put("Deleted Successfully", Boolean.TRUE);
            return response;
        }catch (Exception e){
            response.put("Failed to delete", Boolean.FALSE);
            return response;
        }
    }

    @PostMapping("/{idcustomer}")
    public Order addOrder(@PathVariable Long idcustomer, @RequestBody Order order, @RequestParam List<Long> ids){
        for (int i = 0; i <= ids.size(); i++){
            Product product = productService.findProductById(ids.get(i));
            order.addproduct(product);
        }
        Customer customer = customerService.findCustomerById(idcustomer);
        order.setCustomer(customer);

        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order){
        Order order1 = orderService.findOrderById(id);
        if (order1 != null){
            order.setCustomer(order1.getCustomer());
            return orderService.updateOrder(order);
        }else
            throw new RuntimeException("Failed to delete this order");
    }
}
