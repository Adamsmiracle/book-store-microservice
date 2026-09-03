package com.bookstore.catalog_service.web.controllers;

import com.bookstore.catalog_service.domain.PagedResult;
import com.bookstore.catalog_service.domain.Product;
import com.bookstore.catalog_service.domain.ProductNotFoundException;
import com.bookstore.catalog_service.domain.ProductService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  ProductController(ProductService productService) {
    this.productService = productService;
  }

    @Operation(summary = "Get a paginated list of products", description = "Returns a paginated list of products, sorted by name in ascending order.")
    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        return productService.getProducts(pageNo);
        }


    @Operation(summary = "Get a product by its code", description = "Returns the product with the specified code, if it exists.")
    @GetMapping("/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code) {
        return productService.getProductByCode(code)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> ProductNotFoundException.forCode(code));
    } 
}
