package com.example.demo.integration.repository;

import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer1;
    private Customer customer2;
    private Customer customer3;

    @BeforeEach
    public void setup() {
        customer1 = new Customer("John", "Smith");
        customer2 = new Customer("Forrest", "Gump");
        customer3 = new Customer("Mike", "Smith");

        entityManager.persist(customer1);
        entityManager.persist(customer2);
        entityManager.persist(customer3);
        entityManager.flush();
    }

    @Test
    public void shouldReturnAllCustomers_whenFindAll() {
        List<Customer> actual = customerRepository.findAll();

        assertThat(actual).hasSize(3);
        assertThat(actual.get(0)).isEqualTo(customer1);
        assertThat(actual.get(1)).isEqualTo(customer2);
        assertThat(actual.get(2)).isEqualTo(customer3);
    }

    @Test
    public void shouldReturnCustomer_whenFindById() {
        Long givenId = (Long) entityManager.getId(customer1);
        Customer actual = customerRepository.findById(givenId).get();

        assertThat(actual).isEqualTo(customer1);
    }

    @Test
    public void shouldNotReturnCustomer_whenFindByNonExistingId() {
        Optional<Customer> actual = customerRepository.findById(9999L);

        assertThat(actual).isEmpty();
    }

    @Test
    public void shouldReturnCustomers_whenFindByName() {
        List<Customer> actual = customerRepository.findByLastName("Smith");

        assertThat(actual).hasSize(2);
        assertThat(actual.get(0)).isEqualTo(customer1);
        assertThat(actual.get(1)).isEqualTo(customer3);
    }

    @Test
    public void shouldFindsByName_whenSaved() {
        // given
        Customer givenCustomer = new Customer("Bred", "Pit");
        customerRepository.save(givenCustomer);

        // when
        List<Customer> actual = customerRepository.findByLastName("Pit");

        // then
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0)).isEqualTo(givenCustomer);
        assertThat(actual.get(0).getId()).isNotNull();
    }

    @Test
    public void shouldDeleteCustomer_whenDeleteById() {
        // given
        Long givenId = (Long) entityManager.getId(customer1);
        Optional<Customer> actual = customerRepository.findById(givenId);
        assertThat(actual.get()).isEqualTo(customer1);

        // when
        customerRepository.deleteById(givenId);

        // then
        Optional<Customer> deleted = customerRepository.findById(givenId);
        assertThat(deleted).isEmpty();
    }
}