package com.ecommerce.royalshop.controllers;

import com.ecommerce.royalshop.models.Customer;
import com.ecommerce.royalshop.services.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerServiceImpl customerService;

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id){
        return customerService.findCustomerById(id);
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.findAllCustomers();
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteCustomer(@PathVariable Long id){
        Map<String, Boolean> response = new HashMap<>();
        customerService.deleteCustomerById(id);
        try {
            response.put("Deleted Succeessfully", Boolean.TRUE);
            return response;
        }catch (Exception e){
            response.put("Failed to delete", Boolean.FALSE);
            return response;
        }
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer){
        return customerService.createCustomer(customer);
    }

    @PutMapping
    public Customer updateCustomer(@PathVariable Long id ,@RequestBody Customer customer){
        Customer customer1 = customerService.findCustomerById(id);
        if (customer1 != null){
            return customerService.createCustomer(customer);
        }else
        throw new RuntimeException("Failed to update this customer");
    }
}
