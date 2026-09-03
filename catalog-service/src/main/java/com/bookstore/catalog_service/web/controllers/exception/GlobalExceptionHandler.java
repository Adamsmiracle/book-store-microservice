package com.bookstore.catalog_service.web.controllers.exception;

import java.net.URI;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bookstore.catalog_service.domain.ProductNotFoundException;

@RestControllerAdvice
 class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final URI NOT_FOUND_TYPE = URI.create("https://api.bookstore.com/errors/not-found");
    private static final URI ISE_FOUND_TYPE = URI.create("https://api.bookstore.com/errors/server-error");
    private static final String SERVICE_NAME = "catalog_service";

    // It is advisa to have a generic exception handler to catch any unhandled exceptions and return a standardized error response.
    @ExceptionHandler(Exception.class)
    ProblemDetail handleUnhandledException(Exception e) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            problemDetail.setTitle("Internal Server Error");
            problemDetail.setType(ISE_FOUND_TYPE);
            problemDetail.setProperty("service", SERVICE_NAME);
            problemDetail.setProperty("error_category", "Generic");
            problemDetail.setProperty("timestamp", Instant.now());
            return problemDetail;

    }

    // this exception handler is specifically for handling ProductNotFoundException, which is thrown when a product with a specified code is not found in the catalog. It returns a 404 Not Found status along with a detailed error message.
    @ExceptionHandler(ProductNotFoundException.class)
    ProblemDetail handleProductNotFoundException(ProductNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Product Not Found");
        problemDetail.setType(NOT_FOUND_TYPE);
        problemDetail.setProperty("service", SERVICE_NAME);
        problemDetail.setProperty("error_category", "Not Found");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
    
}
