package com.microservice.ecommerce.exceptions;

public class ProductPurchaseException extends RuntimeException {
    public ProductPurchaseException(String message) {
        super(message);
    }
}
