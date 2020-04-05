package com.example.demo.integration.webmvc;

import com.example.demo.domain.Customer;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.rest.CustomerController;
import com.example.demo.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Strategy 1. MockMVC webmvc code example
 * Not loading any context
 * https://mkyong.com/spring-boot/spring-rest-integration-test-example/
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerStandaloneIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("customerServiceImpl")
    private CustomerService customerService;

    private JacksonTester<Customer> jsonCustomer;
    private JacksonTester<List> jsonCustomers;

    @BeforeEach
    public void setup() {
        // We would need this line if we would not use MockitoJUnitRunner
        // MockitoAnnotations.initMocks(this);
        // Initializes the JacksonTester
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void canFindById_whenExist() throws Exception {
        // given
        Customer givenCustomer = new Customer("test_name", "test_last_name");
        when(customerService.findById(5L)).thenReturn(givenCustomer);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/customer?id=5").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonCustomer.write(givenCustomer).getJson());
    }

    @Test
    public void shouldShowExceptionMessage_whenFindById_andDoesNotExist() throws Exception {
        // given
        when(customerService.findById(997L)).thenThrow(new CustomerNotFoundException(997L));

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/customer?id=997").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString())
                .isEqualTo("could not find customer 997");
    }

    @Test
    public void canFindByName_whenExist() throws Exception {
        // given
        List<Customer> givenCustomers = Arrays.asList(
                new Customer("test_name", "test_last_name"),
                new Customer("test_name", "test_last_name"));

        when(customerService.findByLastName("test_last_name")).thenReturn(givenCustomers);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/customers?lastName=test_last_name").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonCustomers.write(givenCustomers).getJson());
    }

//    @Test
//    public void shouldReturnCustomer_whenFindById() throws Exception {
//        Customer givenCustomer = new Customer("test_name", "test_last_name");
//        when(customerService.findById(5L)).thenReturn(givenCustomer);
//
//        mockMvc.perform(get("/customer?id=5"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("Hello, World")));
//    }
}