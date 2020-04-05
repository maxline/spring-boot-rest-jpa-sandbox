package com.example.demo.unit.service;

import com.example.demo.domain.Customer;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerServiceImpl testInstance;

    @Test
    void shouldCallCustomerRepository_whenFindAll() {
        testInstance.findAll();
        verify(customerRepository).findAll();
    }

    @Test
    void shouldCallCustomerRepository_whenFindByLastName() {
        testInstance.findByLastName("test_last_name");
        verify(customerRepository).findByLastName("test_last_name");
    }

    @Test
    void shouldCallCustomerRepository_whenFindById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(new Customer()));
        testInstance.findById(1L);
        verify(customerRepository).findById(1L);
    }

    @Test
    void shouldThrowCustomerNotFoundException_whenIdDoesNotExist() {
        //when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> testInstance.findById(1L))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("could not find customer 1");
    }

    @Test
    void shouldCallCustomerRepository_whenDeleteById() {
        testInstance.deleteById(1L);
        verify(customerRepository).deleteById(1L);
    }


}