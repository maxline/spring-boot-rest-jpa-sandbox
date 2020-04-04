package com.example.demo.repository;

import com.example.demo.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findAll();

    List<Customer> findByLastName(String lastName);

    //Customer findById(long id);  // todo conflict with .orElseThrow(() -> new CustomerNotFoundException(id));
}
