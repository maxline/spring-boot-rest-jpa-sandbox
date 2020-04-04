package com.example.demo.rest;

import com.example.demo.domain.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(@Qualifier("employeeServiceImpl") EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/employees")
    public List<Employee> findAll() {
        return service.findAll();
    }

    @PostMapping("/employees")
    public Employee save(@RequestBody Employee newEmployee) {
        return service.save(newEmployee);
    }

    @GetMapping("/employees/{id}")
    public Employee findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteById(id);
    }
}
