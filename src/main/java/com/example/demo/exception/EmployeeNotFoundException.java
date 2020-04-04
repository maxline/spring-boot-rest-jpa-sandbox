package com.example.demo.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("could not find employee " + id);
    }
}
