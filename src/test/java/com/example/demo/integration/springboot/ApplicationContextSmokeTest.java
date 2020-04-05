package com.example.demo.integration.springboot;

import com.example.demo.rest.GreetingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Strategy 4: SpringBootTest with a Real Web Server
 * SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
 * TestRestTemplate
 * https://thepracticaldeveloper.com/2017/07/30/guide-spring-boot-controller-tests/
 * https://www.baeldung.com/spring-boot-testresttemplate
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationContextSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GreetingController controller;

    @Test
    public void applicationContextShouldStartAndCreateController() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void httpRequestShouldReturnDefaultMessage() {
        String actual = this.restTemplate.getForObject("http://localhost:" + port + "/greeting", String.class);
        System.out.println(actual);
        assertThat(actual).contains("Hello, World!");
    }
}