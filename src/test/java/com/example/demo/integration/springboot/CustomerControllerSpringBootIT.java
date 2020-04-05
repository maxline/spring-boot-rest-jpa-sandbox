package com.example.demo.integration.springboot;

import com.example.demo.domain.Customer;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Strategy 4: SpringBootTest with a Real Web Server
 * SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
 * TestRestTemplate
 * https://thepracticaldeveloper.com/2017/07/30/guide-spring-boot-controller-tests/
 * https://www.baeldung.com/spring-boot-testresttemplate
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerSpringBootIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    @Qualifier("customerServiceImpl")
    private CustomerService customerService;

//    @TestConfiguration
//    static class CustomerServiceImplTestContextConfiguration {
//
//        @Bean
//        public CustomerService testInstance() {
//            return new CustomerServiceImpl(customerRepository);
//        }
//    }

    @Test
    public void canRetrieveByIdWhenExists() {
        // given
        Customer givenCustomer = new Customer("test_name", "test_last_name");
        when(customerService.findById(2)).thenReturn(givenCustomer);

        // when
        Customer actualCustomer = restTemplate.getForObject("/customer?id=2", Customer.class);

        // then
        assertThat(actualCustomer).isEqualTo(givenCustomer);
    }

    @Test
    public void canRetrieveByLastNameIdWhenExists() {
        // given
        List<Customer> givenCustomers = Arrays.asList(
                new Customer("test_name", "test_last_name"),
                new Customer("test_name", "test_last_name"));

        when(customerService.findByLastName("test_last_name")).thenReturn(givenCustomers);

        // when
        ResponseEntity<Customer[]> response = restTemplate.getForEntity("/customers?lastName=test_last_name", Customer[].class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()[0]).isEqualTo(givenCustomers.get(0));
        assertThat(response.getBody()[1]).isEqualTo(givenCustomers.get(1));
    }

    @Test
    public void canRetrieveByIdWhenDoesNotExist() {
        // given
        when(customerService.findById(997L)).thenThrow(new CustomerNotFoundException(997L));

        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/customer?id=997", String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("could not find customer 997");
    }

    @Test
    public void canCreateANewCustomer() {
        String url = "/customers";
        Customer givenCustomer = new Customer("test_name", "test_last_name");

        ResponseEntity<Customer> response = restTemplate.postForEntity(url, givenCustomer, Customer.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED); // todo how to implement status CREATED?
    }
}