package com.microservice.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Product id should be present")
        Integer productId,
        @Positive(message = "Quantity should be present")
        double quantity


) {
}
