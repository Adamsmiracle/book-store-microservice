package com.bookstore.catalog_service;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

// Only use this class as a base class for integration tests. It sets up the test environment and
// configures RestAssured to use the random port assigned to the application during testing.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ContainersConfig.class)
public abstract class AbstractIT {
  @LocalServerPort int port;

  @BeforeEach
  void setup() {
    RestAssured.port = port;
  }
}
