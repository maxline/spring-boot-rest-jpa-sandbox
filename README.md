# Simple spring boot application example

## Run the Application
If you use Maven, run the following command in a terminal window (in the project) directory:
```
./mvnw spring-boot:run
```
or just run `RunApplication.java` class from your IDE.

## Links for testing how app is working
http://localhost:8080/greeting <br>
http://localhost:8080/greeting?name=John <br>
http://localhost:8080/customer?id=6 <br>
http://localhost:8080/customers find all customers <br>
http://localhost:8080/customers?lastName=Bauer  find all customers by lastName filter <br>
http://localhost:8080/employees/1 <br>
http://localhost:8080/employees/3 output "could not find employee 3" <br>

## Curl commands
Find Employee record by id command
```
curl -v localhost:8080/employees/99
```
will nicely shows an HTTP 404 error with the custom message "Could not find employee 99"

Create a new Employee record
```
$ curl --header "Content-Type: application/json" --request POST --data '{"name":"name111","role":"manager111"}' http://localhost:8080/employees
```
or
```
$ curl --header "Content-Type: application/json" --request POST --data @body.json http://localhost:8080/employees
```
where body.json is file with this content:
```
{"name":"name111","role":"manager111"}
```
and  sends the content back to us:
```
{"id":3,"name":"Samwise Gamgee","role":"gardener"}
```

And you can delete…​
```
$ curl -X DELETE localhost:8080/employees/3
$ curl localhost:8080/employees/3
Could not find employee 3

$ curl -X DELETE localhost:8080/customers?id=3
```

## Useful links
https://spring.io/guides/gs/spring-boot/ 1. Building an Application with Spring Boot (brief) <br>
https://spring.io/guides/gs/rest-service/ 2. Building a RESTful Web Service (brief) <br>
https://spring.io/guides/tutorials/rest/ 3. Building REST services with Spring (detailed) <br>
https://spring.io/guides/gs/accessing-data-jpa/ 4. Accessing Data with JPA (brief) <br>
https://www.baeldung.com/spring-request-param 5. Spring @RequestParam Annotation <br>
https://www.baeldung.com/spring-requestmapping 6. Spring RequestMapping <br>

https://www.baeldung.com/spring-boot-testing Testing in Spring Boot <br>
https://docs.spring.io/spring-boot/docs/2.1.5.RELEASE/reference/html/boot-features-testing.html  Part IV. Spring Boot features 46. Testing <br>
https://junit.org/junit5/docs/current/user-guide/ JUnit 5 User Guide <br>


- // add unit tests CustomerControllerSpringBootIT (restTemplate.getForObject), CustomerControllerStandaloneIT
- // todo remove injectMocks if possible
- // todo test controller https://www.baeldung.com/exception-handling-for-rest-with-spring Error Handling for REST with Spring
- // todo controller test https://howtodoinjava.com/spring-boot2/testing/spring-mockbean-annotation/
- // todo transactional
- // todo add hateoas from 3 to make app RESTful
- // push to the git, add git ignore
- // todo interfaces
- // add inmemory DB from Rodionov, add fabric for choosing different repositories
- // add property file for configuring app

- // http://localhost:8080/employee create return for error page
- // http://localhost:8080/customer create return for error page
