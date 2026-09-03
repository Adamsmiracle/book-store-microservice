package com.bookstore.catalog_service.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  //  Get a product by its code.
  Optional<ProductEntity> findByCode(String code);
}
