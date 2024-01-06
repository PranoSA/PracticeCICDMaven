package com.compressibleflowcalculator;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import io.restassured.RestAssured;

//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;

import java.util.Arrays;
import java.util.List;
/*import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;s
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
*/
import org.testcontainers.containers.PostgreSQLContainer;

import com.compressibleflowcalculator.Entities.Group;
import com.compressibleflowcalculator.Repository.GroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Include The Repositories

// Include The Entities
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine");

    @BeforeAll
    public static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    public static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    GroupRepository customerRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;

        // customerRepository.deleteAll();
        // System.out.println(context);
    }

    @Test
    public void shouldReturnAllCustomers() {

        List<Group> customers = Arrays.asList(
                new Group("John", "john@mail.com"),
                new Group("Dennis", "dennis@mail.com"));
        customerRepository.saveAll(customers);

        List<Group> previouslySavedGroups = customerRepository.findAll();
        System.out.println("Find Previously Saved");
        System.out.println(previouslySavedGroups);

        given().port(port).when().get("/getgroups").then().statusCode(200).body("$",
                hasSize(2));
    }

}