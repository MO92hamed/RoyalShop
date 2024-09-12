package com.ecommerce.royalshop.services.impl;

import com.ecommerce.royalshop.models.Customer;
import com.ecommerce.royalshop.repositories.CustomerRepository;
import com.ecommerce.royalshop.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    @Override
    public Customer findCustomerById(Long id) {
        return customerRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Customer with id [%s] is not found in our database")));
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
