package com.example.demo.rest;

import com.example.demo.domain.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService service;

    public CustomerController(@Qualifier("customerServiceImpl") CustomerService service) {
        this.service = service;
    }

    @GetMapping("/customer")
    public Customer findById(@RequestParam Long id) {
        return service.findById(id);
    }

    @GetMapping("/customers")
    public List<Customer> findByFilter(@RequestParam(required = false) String lastName) {
        if (lastName != null) {
            return service.findByLastName(lastName);
        }
        return service.findAll();
    }

    // todo what if id not exist, can't use orElseThrow as in findById because deleteById don't return result
    // todo try catch in service class also doesn't work
    // todo org.springframework.dao.EmptyResultDataAccessException: No class com.example.demo.domain.Customer entity with id 99 exists!
    @DeleteMapping("/customers")
    public void deleteEmployee(@RequestParam Long id) {
        service.deleteById(id);
    }

}