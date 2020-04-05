package com.example.demo.unit.service;

import com.example.demo.domain.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl testInstance;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    void shouldCallEmployeeRepository_whenFindAll() {
        testInstance.findAll();
        verify(employeeRepository).findAll();
    }

    @Test
    void shouldCallEmployeeRepository_whenFindById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(new Employee()));
        testInstance.findById(1L);
        verify(employeeRepository).findById(1L);
    }

    @Test
    void shouldCallEmployeeRepository_whenSave() {
        Employee givenEmployee = new Employee("name", "role");
        testInstance.save(givenEmployee);
        verify(employeeRepository).save(givenEmployee);
    }

    @Test
    void shouldCallEmployeeRepository_whenDeleteById() {
        testInstance.deleteById(1L);
        verify(employeeRepository).deleteById(1L);
    }
}