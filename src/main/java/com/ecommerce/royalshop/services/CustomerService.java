package com.ecommerce.royalshop.services;

import com.ecommerce.royalshop.models.Customer;

import java.util.List;

public interface CustomerService {
    Customer findCustomerById(Long id);
    List<Customer> findAllCustomers();
    void deleteCustomerById(Long id);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
}
