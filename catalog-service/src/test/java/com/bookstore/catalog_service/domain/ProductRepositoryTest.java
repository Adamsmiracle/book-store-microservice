package com.bookstore.catalog_service.domain;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import com.bookstore.catalog_service.ContainersConfig;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest(
    properties = {
        "spring.test.database.replace=NONE",

        // We use Testcontainers to run a PostgreSQL database in a Docker container for our tests. The "jdbc:tc" URL prefix tells Spring Boot to use Testcontainers to start a PostgreSQL container and connect to it. The "postgresql:16-alpine" part specifies the Docker image to use for the PostgreSQL container, and the "///bookstore" part specifies the name of the database to create in the container.
        "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///bookstore"
    }
)


// Use the `spring.datasource.url` property when the test itself needs an ad hoc database setup,
// such as wiring Testcontainers directly in the test class via a JDBC URL like
// `jdbc:tc:postgresql:16-alpine:///bookstore`. This is convenient for a single test class or
// a very local, self-contained configuration.
//
// Use `@Import(ContainersConfig.class)` when the PostgreSQL container configuration is defined in a
// reusable configuration class and should be shared across multiple tests or modules. In that case,
// the container lifecycle and datasource bean are managed centrally instead of being repeated in each
// test class.


// @Import(ContainersConfig.class)
@Sql("/test-data.sql")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldGetAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        assertThat(products).hasSize(15);
    }

    @Test
    void shouldGetProductByCode() {
        ProductEntity product = productRepository.findByCode("P100").orElseThrow();
        assertThat(product.getCode()).isEqualTo("P100");
        assertThat(product.getName()).isEqualTo("The Hunger Games");
        assertThat(product.getDescription()).isEqualTo("Winning will make you famous. Losing means certain death...");
        assertThat(product.getPrice()).isEqualByComparingTo(new BigDecimal("34.0"));
    }

    @Test
    void shouldReturnEmptyProductCodeNotExist() {
        assertThat(productRepository.findByCode("Invalid_product_code")).isEmpty();
    }
}
