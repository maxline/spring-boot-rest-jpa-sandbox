package com.example.demo.service;

import com.example.demo.domain.Customer;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Customer> findByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }

    @Override
    public Customer findById(long id) {
        return repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
