package com.example.demo.service;

import com.example.demo.domain.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();

    Employee findById(long id);

    Employee save(Employee newEmployee);

    void deleteById(Long id);
}