package com.example.demo.service;

import com.example.demo.domain.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();

    List<Customer> findByLastName(String lastName);

    Customer findById(long id);

    Customer save(Customer newCustomer);

    void deleteById(Long id);
}
